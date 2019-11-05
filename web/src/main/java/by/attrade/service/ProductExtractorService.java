package by.attrade.service;

import by.attrade.config.ServerPathConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Picture;
import by.attrade.domain.Product;
import by.attrade.domain.ProductProperty;
import by.attrade.domain.Property;
import by.attrade.io.ImageDownloader;
import by.attrade.service.jsoup.IProductExtractor;
import by.attrade.service.jsoup.JsoupDocService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductExtractorService {
    private static final String DESCRIPTION = "описание";
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPropertyService productPropertyService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ImageDownloader imageDownloader;

    @Autowired
    private ServerPathConfig serverPathConfig;

    @Autowired
    private JsoupDocService jsoupDocService;


    public void saveProductsIfNotExistsByCode(IProductExtractor extractor, List<String> urls) {
        for (String url : urls) {
            try {
                saveProductIfNotExistsByCode(extractor, url);
            } catch (Exception e) {
                log.error(url, e);
            }
        }
    }

    public void saveProductIfNotExistsByCode(IProductExtractor extractor, String url) throws IOException {
        Document doc = jsoupDocService.getJsoupDoc(url);
        Product product = extractor.getProduct(doc);
        if (productService.existsByCode(product)) {
            return;
        }
        saveProduct(extractor, doc, url);

    }

    private void saveProduct(IProductExtractor extractor, Document doc, String url) throws IOException {
        List<Category> categories = extractor.getCategories(doc);
        Category category = categoryService.saveShaneOfCategory(categories);
        List<String> imagesUrl = extractor.getImagesUrl(doc);
        List<Picture> pictures = new ArrayList<>();
        int priority = 0;
        for (String imageUrl : imagesUrl) {
            String extension = FilenameUtils.getExtension(imageUrl);
            String name = UUID.randomUUID().toString() + "." + extension;
            String pathName = serverPathConfig.getAbsolute() + serverPathConfig.getPicture() + File.separator + name;
            imageDownloader.download(imageUrl, pathName);
            pictures.add(new Picture(name, priority++));
        }
        pictures = pictureService.saveAll(pictures);

        List<Property> properties = extractor.getProperties(doc);
        List<String> values = extractor.getPropertiesValue(doc);
        String description = getAndRemoveDescription(properties, values);
        properties = propertyService.saveAll(properties, category);

        Product product = extractor.getProduct(doc);
        product.setDescription(description);
        product.setCategory(category);
        product.setUrl(url);
        product.setPictures(pictures);
        product.setPicture(pictures.get(0).getPath());
        productService.save(product);


        int size = properties.size();
        for (int i = 0; i < size; i++) {
            ProductProperty productProperty = new ProductProperty();
            productProperty.setProduct(product);
            productProperty.setProperty(properties.get(i));
            productProperty.setData(values.get(i));
            try {
                productPropertyService.save(productProperty);
            } catch (Exception e) {
                log.error(e.getMessage(), productProperty);
            }
        }
    }

    private String getAndRemoveDescription(List<Property> properties, List<String> values) {
        String description = null;
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            if (p.getName().equalsIgnoreCase(DESCRIPTION)) {
                description = values.get(i);
                properties.remove(i);
                values.remove(i);
            }
        }
        return description;
    }
}
