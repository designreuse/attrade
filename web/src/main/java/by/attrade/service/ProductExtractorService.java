package by.attrade.service;

import by.attrade.config.ServerLanguageConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Dimension;
import by.attrade.domain.ExtractorError;
import by.attrade.domain.ExtractorErrorUrl;
import by.attrade.domain.Picture;
import by.attrade.domain.Product;
import by.attrade.domain.ProductProperty;
import by.attrade.domain.Property;
import by.attrade.domain.PropertyData;
import by.attrade.service.categoryPathAdjuster.CategoryPathByNameAdjusterService;
import by.attrade.service.exception.NoPotentialProductException;
import by.attrade.service.jsoup.IProductExtractor;
import by.attrade.service.jsoup.JsoupDocService;
import by.attrade.service.jsoup.extractor.ExtractorErrorService;
import by.attrade.service.jsoup.extractor.ExtractorErrorUrlService;
import by.attrade.service.productPathExtractor.UrlOfNameProductPathExtractorService;
import by.attrade.type.Unit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductExtractorService {
    private static final String DESCRIPTION = "описание";
    private static final int COUNT_IN_BATCH = 100;


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
    private UrlOfNameProductPathExtractorService productPathExtractor;

    @Autowired
    private PictureMediaService pictureMediaService;

    @Autowired
    private CategoryPathByNameAdjusterService categoryPathAdjusterService;

    @Autowired
    private ServerLanguageConfig serverLanguageConfig;

    public boolean isPotentialProduct(Object... nullEvaluated) {
        return ObjectUtils.allNotNull(nullEvaluated);
    }

    public Product getProduct(IProductExtractor extractor, Document doc) throws NoPotentialProductException {
        String code = extractor.getCode(doc);
        String name = extractor.getName(doc);
        if (!isPotentialProduct(code, name)) {
            throw new NoPotentialProductException("One of necessary product fields evaluate as null.");
        }
        String vendorCode = extractor.getVendorCode(doc);
        Unit unit = extractor.getUnit(doc);
        Integer count = extractor.getCount(doc);
        Dimension dimension = extractor.getDimension(doc);
        Double weight = extractor.getWeight(doc);
        Unit unitPack = extractor.getUnitInPack(doc);
        Integer countInPack = extractor.getCountInPack(doc);
        Dimension dimensionPack = extractor.getDimensionPack(doc);
        Double weightPack = extractor.getWeightPack(doc);
        Unit unitCarry = extractor.getUnitCarry(doc);
        Integer countInPackCarry = extractor.getCountInPackCarry(doc);
        Dimension dimensionCarry = extractor.getDimensionCarry(doc);
        Double weightCarry = extractor.getWeightCarry(doc);
        String barcode = extractor.getBarcode(doc);
        String description = extractor.getDescription(doc);

        Product product = new Product();
        product.setCode(code);
        product.setVendorCode(vendorCode);
        product.setName(name);
        product.setUnit(unit);
        product.setCount(count);
        product.setDimension(dimension);
        product.setWeight(weight);
        product.setUnitPack(unitPack);
        product.setCountInPack(countInPack);
        product.setDimensionPack(dimensionPack);
        product.setWeightPack(weightPack);
        product.setUnitCarry(unitCarry);
        product.setCountInPackCarry(countInPackCarry);
        product.setDimensionCarry(dimensionCarry);
        product.setWeightCarry(weightCarry);
        product.setBarcode(barcode);
        product.setDescription(description);
        return product;
    }

    public ExtractorError saveProductsIfNotExistsByCodeAndSaveErrors(IProductExtractor extractor) {
        List<ExtractorErrorUrl> errorUrls = saveProductsIfNotExistsByCode(extractor);
//        adjustCategoriesPaths(extractor); //TODO -throw
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

    private List<ExtractorErrorUrl> saveProductsIfNotExistsByCode(IProductExtractor extractor) {
        Set<String> all = new ConcurrentSkipListSet<>();
        Set<String> selected = new ConcurrentSkipListSet<>();
        all.add(extractor.getUrl());
        selected.addAll(productService.getUrlsStartWith(extractor.getUrl()));
        List<ExtractorErrorUrl> errorUrls = new LinkedList<>();
        while (!all.isEmpty()) {
            List<String> batchUrls = all.stream().limit(COUNT_IN_BATCH).collect(Collectors.toList());
            log.info("ALL: " + all.size() + " TRY EXTRACT: " + batchUrls);
            selected.addAll(batchUrls);
            all.removeAll(batchUrls);
            batchUrls.stream().parallel().forEach(url ->tryExtractAndSave(extractor, all, selected, errorUrls, url));
        }
        return errorUrls;
    }

    private void tryExtractAndSave(IProductExtractor extractor, Set<String> all, Set<String> selected, List<ExtractorErrorUrl> errorUrls, String url) {
        Document doc;
        try {
            doc = jsoupDocService.getJsoupDoc(url);
            extractUrls(doc, all, selected, extractor);
        } catch (Exception e) {
            errorUrls.add(new ExtractorErrorUrl(url, e.getMessage()));
            log.info("Cannot parse jsoup doc: " + url, e);
            return;
        }
        try {
            saveProductIfNotExistsByCode(extractor, doc);
        } catch (Exception e) {
            errorUrls.add(new ExtractorErrorUrl(url, e.getMessage()));
            log.info("Cannot save extracted product because cannot fetch: " + url, e);
            return;
        }
    }

    private void extractUrls(Document doc, Set<String> all, Set<String> selected, IProductExtractor extractor) {
        Stream<String> allHrefNotPdfNotImage = jsoupDocService.getAllHrefNotPdfNotImage(doc, extractor.isHrefExtractsByFileLoading());
        allHrefNotPdfNotImage.filter(e -> !selected.contains(e) && e.contains(extractor.getUrl())).forEach(all::add);
    }

    public ExtractorError saveProductsIfNotExistsByCodeAndSaveErrors(IProductExtractor extractor, Stream<String> urls) {
        List<ExtractorErrorUrl> errorUrls = saveProductsIfNotExistsByCode(extractor, urls);
//        adjustCategoriesPaths(extractor); //TODO -throw
        return saveErrorUrls(errorUrls);
    }

    public List<ExtractorErrorUrl> saveProductsIfNotExistsByCode(IProductExtractor extractor, Stream<String> urls) {
        List<ExtractorErrorUrl> errorUrls = new LinkedList<>();
        urls.forEach(url -> {
                    try {
                        saveProductIfNotExistsByCode(extractor, url);
                    } catch (HttpStatusException e) {
                        errorUrls.add(new ExtractorErrorUrl(url, e.getMessage()));
                        log.info("Cannot save extracted product because cannot fetch: " + url, e);
                    } catch (Exception e) {
                        log.error("Cannot save extracted product: " + url, e);
                    }
                }
        );

        return errorUrls;
    }

    public void saveProductIfNotExistsByCode(IProductExtractor extractor, String url) throws Exception {
        Document doc = jsoupDocService.getJsoupDoc(url);
        saveProductIfNotExistsByCode(extractor, doc);
    }

    public void saveProductIfNotExistsByCode(IProductExtractor extractor, Document doc) throws Exception {
        String code = extractor.getCode(doc);
        if (code == null || productService.existsByCode(code)) {
            return;
        }
        saveProductOrRollback(extractor, doc);
    }

    private ExtractorError saveErrorUrls(List<ExtractorErrorUrl> errorUrls) {
        List<ExtractorErrorUrl> saveErrorUrls = urlService.saveAll(errorUrls);
        ExtractorError extractorError = new ExtractorError();
        extractorError.setUrls(saveErrorUrls);
        return errorService.save(extractorError);
    }

    private void saveProductOrRollback(IProductExtractor extractor, Document doc) throws NoPotentialProductException {
        Product product = getProduct(extractor, doc);
        List<Category> categories = extractor.getCategories(doc);
        List<String> imagesUrl = extractor.getImagesUrl(doc);
        List<String> descriptionImagesUrl = extractor.getDescriptionImagesUrl(doc);
        List<Property> properties = extractor.getProperties(doc);
        List<String> values = extractor.getPropertyValues(doc);

        List<Picture> pictures = downloadPictures(imagesUrl);
        List<Picture> descriptionPictures = downloadPictures(descriptionImagesUrl);
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
        product.setUrl(doc.location());
        product.setPath(path);
        product.setDescription(description);
        if (!pictures.isEmpty()) {
            product.setPicture(pictures.get(0).getPath());
        }
        productService.save(product);

        for (Picture p : pictures) {
            p.setProductPicture(product);
        }
        pictureService.saveAll(pictures);

        for (Picture p : descriptionPictures) {
            p.setProductDescriptionPicture(product);
        }
        pictureService.saveAll(descriptionPictures);

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

    private List<Picture> downloadPictures(List<String> imagesUrl) {
        List<Picture> pictures = new ArrayList<>();
        int priority = 0;
        for (String imageUrl : imagesUrl) {
            String name = pictureMediaService.savePictureOrRollback(imageUrl);
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
