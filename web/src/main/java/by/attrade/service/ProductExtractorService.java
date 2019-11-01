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


    public void saveProducts(IProductExtractor extractor, List<String> urls) {
        for (String url : urls) {
            try {
                saveProduct(extractor, url);
            } catch (Exception e) {
                log.error(url, e);
            }
        }
    }

    public void saveProduct(IProductExtractor extractor, String url) throws IOException {
        Document doc = jsoupDocService.getJsoupDoc(url);
        List<Category> categories = extractor.getCategories(doc);
        Category category = categoryService.saveShaneOfCategory(categories);
        String relPath = serverPathConfig.getUpload() + serverPathConfig.getProduct();
        List<String> imagesUrl = extractor.getImagesUrl(doc);
        List<Picture> pictures = new ArrayList<>();
        int priority = 0;
        for (String imageUrl : imagesUrl) {
            String extension = FilenameUtils.getExtension(imageUrl);
            String name = File.separator + UUID.randomUUID().toString() + "." + extension;
            imageDownloader.download(imageUrl, serverPathConfig.getAbsolute() + relPath + name);
            pictures.add(new Picture(relPath, name, priority++));
        }
        pictures = pictureService.saveAll(pictures);
        Product product = extractor.getProduct(doc);
        product.setCategory(category);
        product.setUrl(url);
        product.setPictures(pictures);
        product.setPicture(pictures.get(0));
        productService.save(product);

        List<Property> properties = extractor.getProperties(doc);
        properties = propertyService.saveAll(properties, category);

        List<String> values = extractor.getPropertiesValue(doc);
        int size = properties.size();
        List<ProductProperty> productProperties = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ProductProperty productProperty = new ProductProperty();
            productProperty.setProduct(product);
            productProperty.setProperty(properties.get(i));
            productProperty.setData(values.get(i));
            productProperties.add(productProperty);
        }
        productPropertyService.saveAll(productProperties);
    }
}
