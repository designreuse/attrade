package by.attrade.service;

import by.attrade.config.ServerLanguageConfig;
import by.attrade.domain.Category;
import by.attrade.domain.ExtractorError;
import by.attrade.domain.ExtractorErrorUrl;
import by.attrade.domain.Picture;
import by.attrade.domain.Product;
import by.attrade.domain.ProductProperty;
import by.attrade.domain.Property;
import by.attrade.domain.PropertyData;
import by.attrade.service.categoryPathAdjuster.CategoryPathByNameAdjusterService;
import by.attrade.service.jsoup.IProductExtractor;
import by.attrade.service.jsoup.JsoupDocService;
import by.attrade.service.jsoup.extractor.ExtractorErrorService;
import by.attrade.service.jsoup.extractor.ExtractorErrorUrlService;
import by.attrade.service.productPathExtractor.Utf8OfNameProductPathExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
    private JsoupDocService jsoupDocService;

    @Autowired
    private ExtractorErrorService errorService;

    @Autowired
    private ExtractorErrorUrlService urlService;

    @Autowired
    private Utf8OfNameProductPathExtractorService productPathExtractor;

    @Autowired
    private PictureMediaService pictureMediaService;

    @Autowired
    private CategoryPathByNameAdjusterService categoryPathAdjusterService;

    @Autowired
    private ServerLanguageConfig serverLanguageConfig;

    public ExtractorError saveProductsIfNotExistsByCodeAndSaveErrors(IProductExtractor extractor, double[] compressions) {
        List<ExtractorErrorUrl> errorUrls = saveProductsIfNotExistsByCode(extractor, compressions);
        adjustCategoriesPaths(extractor);
        return saveErrorUrls(errorUrls);
    }

    private void adjustCategoriesPaths(IProductExtractor extractor) {
        try {
            String language = extractor.getLocale().getLanguage();
            List<Category> categories = categoryService.findAll();
            categoryPathAdjusterService.adjustPaths(categories, language, serverLanguageConfig.getLanguage());
        } catch (Exception e) {
            log.error("Cannot adjust categories paths.", e);
        }
    }

    private List<ExtractorErrorUrl> saveProductsIfNotExistsByCode(IProductExtractor extractor, double[] compressions) {
        Set<String> all = new HashSet<>();
        Set<String> selected = new HashSet<>();
        all.add(extractor.getUrl());
        selected.addAll(productService.getUrlsStartWith(extractor.getUrl()));
        List<ExtractorErrorUrl> errorUrls = new LinkedList<>();
        while (!all.isEmpty()) {
            String url = all.iterator().next();
            if (all.size()%10000==0){
                System.out.println("All: "+all.size());
            }
            all.remove(url);
            selected.add(url);
            Document doc;
            try {
                doc = jsoupDocService.getJsoupDoc(url);
            } catch (HttpStatusException e) {
                errorUrls.add(new ExtractorErrorUrl(url));
                continue;
            } catch (Exception e) {
                continue;
            }
            try {
                saveProductIfNotExistsByCode(extractor, doc, url, compressions);
            } catch (HttpStatusException e) {
                errorUrls.add(new ExtractorErrorUrl(url));
                log.info("Cannot save extracted product because cannot fetch: "+url, e);
            } catch (Exception e) {
                log.error("Cannot save extracted product: "+ url, e);
            }
            extractUrls(doc, all, selected, extractor);
        }
        return errorUrls;
    }

    private void extractUrls(Document doc, Set<String> all, Set<String> selected, IProductExtractor extractor) {
        Stream<String> allHrefNotPdfNotImage = jsoupDocService.getAllHrefNotPdfNotImage(doc);
        allHrefNotPdfNotImage.filter(e -> !selected.contains(e) && e.contains(extractor.getUrl())).forEach(all::add);
    }

    public ExtractorError saveProductsIfNotExistsByCodeAndSaveErrors(IProductExtractor extractor, List<String> urls, double[] compressions) {
        List<ExtractorErrorUrl> errorUrls = saveProductsIfNotExistsByCode(extractor, urls, compressions);
        adjustCategoriesPaths(extractor);
        return saveErrorUrls(errorUrls);
    }

    public List<ExtractorErrorUrl> saveProductsIfNotExistsByCode(IProductExtractor extractor, List<String> urls, double[] compressions) {
        List<ExtractorErrorUrl> errorUrls = new LinkedList<>();
        for (String url : urls) {
            try {
                saveProductIfNotExistsByCode(extractor, url, compressions);
            } catch (HttpStatusException e) {
                errorUrls.add(new ExtractorErrorUrl(url));
            } catch (Exception e) {
                log.info(e.getMessage(), url);
            }
        }

        return errorUrls;
    }

    private ExtractorError saveErrorUrls(List<ExtractorErrorUrl> errorUrls) {
        List<ExtractorErrorUrl> saveErrorUrls = urlService.saveAll(errorUrls);
        ExtractorError extractorError = new ExtractorError();
        extractorError.setUrls(saveErrorUrls);
        return errorService.save(extractorError);
    }

    public void saveProductIfNotExistsByCode(IProductExtractor extractor, Document doc, String url, double[] compressions) throws Exception {
        Product product = extractor.getProduct(doc);
        if (productService.existsByCode(product)) {
            return;
        }
        saveProduct(extractor, doc, url, compressions);
    }

    public void saveProductIfNotExistsByCode(IProductExtractor extractor, String url, double[] compressions) throws Exception {
        Document doc = jsoupDocService.getJsoupDoc(url);
        if (!extractor.isContainProduct(doc)) {
            return;
        }
        Product product = extractor.getProduct(doc);
        if (productService.existsByCode(product)) {
            return;
        }
        saveProduct(extractor, doc, url, compressions);
    }

    private void saveProduct(IProductExtractor extractor, Document doc, String url, double[] compressions) throws Exception {
        List<Category> categories = extractor.getCategories(doc);
        List<String> imagesUrl = extractor.getImagesUrl(doc);
        List<String> descriptionImagesUrl = extractor.getDescriptionImagesUrl(doc);
        List<Property> properties = extractor.getProperties(doc);
        List<String> values = extractor.getPropertyValues(doc);
        Product product = extractor.getProduct(doc);

        List<Picture> pictures = downloadPictures(imagesUrl, compressions);
        List<Picture> descriptionPictures = downloadPictures(descriptionImagesUrl, compressions);
        Category category = categoryService.saveShaneOfCategory(categories);
        pictures = pictureService.saveAll(pictures);
        descriptionPictures = pictureService.saveAll(descriptionPictures);
        String description = product.getDescription();
        if (description == null) {
            description = getAndRemoveDescription(properties, values);
        }
        properties = propertyService.saveAll(properties, category);
        String path = productPathExtractor.getPath(product);

        product.setCategory(category);
        product.setUrl(url);
        product.setPath(path);
        product.setDescription(description);
        product.setPictures(pictures);
        if (!pictures.isEmpty()){
            product.setPicture(pictures.get(0).getPath());
        }
        product.setDescriptionPictures(descriptionPictures);
        productService.save(product);

        int size = properties.size();
        for (int i = 0; i < size; i++) {
            ProductProperty productProperty = new ProductProperty();
            productProperty.setProduct(product);
            productProperty.setProperty(properties.get(i));
            productProperty.setPropertyData(new PropertyData(values.get(i)));
            try {
                productPropertyService.save(productProperty);
            } catch (Exception e) {
                log.error(e.getMessage(), productProperty);
            }
        }
    }

    private List<Picture> downloadPictures(List<String> imagesUrl, double[] compressions) throws IOException {
        List<Picture> pictures = new ArrayList<>();
        int priority = 0;
        for (String imageUrl : imagesUrl) {
            String name = pictureMediaService.savePicture(imageUrl, compressions);
            if (name != null) {
                pictures.add(new Picture(name, imageUrl, priority++));
            }
        }
        return pictures;
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
