package by.attrade.service.jsoup.extractor;

import by.attrade.config.JsoupTexenergoRuProductConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Dimension;
import by.attrade.domain.Product;
import by.attrade.domain.Property;
import by.attrade.service.DimensionParserService;
import by.attrade.service.UnitMessageInSequenceParserService;
import by.attrade.service.jsoup.IProductExtractor;
import by.attrade.type.Unit;
import by.attrade.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class ProductJsoupTexenergoRuExtractor implements IProductExtractor {
    @Autowired
    private JsoupTexenergoRuProductConfig config;
    @Autowired
    private UnitMessageInSequenceParserService unitMessageInSequenceParserService;
    @Autowired
    private DimensionParserService dimensionParserService;

    @Override
    public boolean isContainProduct(Document doc) {
        String code = getCode(doc);
        String name = getName(doc);
        return code != null && name != null;
    }

    @Override
    public Product getProduct(Document doc) {
        String code = getCode(doc);
        String vendorCode = getVendorCode(doc);
        String name = getName(doc);
        Unit unit = getUnit(doc);
        Integer countInPack = getCountInPack(doc);
        Dimension dimension = getDimension(doc);
        Double weight = getWeight(doc);
        Unit unitCarry = getUnitCarry(doc);
        Integer countInPackCarry = getCountInPackCarry(doc);
        Dimension dimensionCarry = getDimensionCarry(doc);
        Double weightCarry = getWeightCarry(doc);
        String barcode = getBarcode(doc);
        String description = getDescription(doc);

        Product product = new Product();
        product.setCode(code);
        product.setVendorCode(vendorCode);
        product.setName(name);
        product.setUnit(unit);
        product.setCountInPack(countInPack);
        product.setDimension(dimension);
        product.setWeight(weight);
        product.setUnitCarry(unitCarry);
        product.setCountInPackCarry(countInPackCarry);
        product.setDimensionCarry(dimensionCarry);
        product.setWeightCarry(weightCarry);
        product.setBarcode(barcode);
        product.setDescription(description);
        return product;
    }

    private Double getWeightCarry(Document doc) {
        try {
            String weightCarryString = doc
                    .getElementsByClass(config.getCarryWeightParent())
                    .select(config.getCarryWeightChild())
                    .select("div:containsOwn(" + config.getCarryWeightDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryWeightDivContains1() + ")").first().parent()
                    .select(config.getCarryWeightValueClass()).get(config.getCarryWeightValueIndex())
                    .text();
            weightCarryString = StringUtil.trimIfNotNull(weightCarryString);
            Double weightCarry = null;
            try {
                weightCarry = Double.parseDouble(weightCarryString);
            } catch (NumberFormatException e) {
                log.error("WeightCarry is not a double: " + doc.location(), e);
            }
            return weightCarry;
        } catch (Exception e) {
            return null;
        }
    }

    private Double getWeight(Document doc) {
        try {
            String weightString = doc
                    .getElementsByClass(config.getWeightParent())
                    .select(config.getWeightChild())
                    .select("div:containsOwn(" + config.getWeightDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getWeightDivContains1() + ")").first().parent()
                    .select(config.getWeightValueClass()).get(config.getWeightValueIndex())
                    .text();
            weightString = StringUtil.trimIfNotNull(weightString);
            Double weight = null;
            try {
                weight = Double.parseDouble(weightString);
            } catch (NumberFormatException e) {
                log.error("Weight is not a double: " + doc.location(), e);
            }
            return weight;
        } catch (Exception e) {
            return null;
        }
    }

    private String getDescription(Document doc) {
        try {
            String description = doc
                    .getElementsByClass(config.getDescriptionClass())
                    .text();
            description = StringUtil.trimIfNotNull(description);
            return description;
        } catch (Exception e) {
            return null;
        }
    }

    private String getBarcode(Document doc) {
        try {
            String barcode = doc
                    .getElementsByClass(config.getBarcodeParent())
                    .select(config.getBarcodeChild())
                    .select("div:containsOwn(" + config.getBarcodeDivContains() + ")").first().parent()
                    .select(config.getBarcodeValueClass()).get(config.getBarcodeValueIndex())
                    .text();
            barcode = StringUtil.trimIfNotNull(barcode);
            return barcode;
        } catch (Exception e) {
            return null;
        }
    }


    private Dimension getDimensionCarry(Document doc) {
        try {
            String dimensionCarryString = doc
                    .getElementsByClass(config.getCarryDimensionParent())
                    .select(config.getCarryDimensionChild())
                    .select("div:containsOwn(" + config.getCarryDimensionDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryDimensionDivContains1() + ")").first().parent()
                    .select(config.getCarryDimensionValueClass()).get(config.getCarryDimensionValueIndex())
                    .text();
            try {
                Dimension dimensionCarry = dimensionParserService.parseXspliterMmToMm(dimensionCarryString);
                return dimensionCarry;
            } catch (NumberFormatException e) {
                log.error("Dimension is not a double: " + doc.location(), e);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getCountInPackCarry(Document doc) {
        try {
            String countCarryInPackString = doc
                    .getElementsByClass(config.getCarryCountInPackParent())
                    .select(config.getCarryCountInPackChild())
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCarryCountInPackValueClass()).get(config.getCarryCountInPackValueIndex())
                    .text();
            try {
                countCarryInPackString = StringUtil.trimIfNotNull(countCarryInPackString);
                Integer countCarryInPack = Integer.valueOf(countCarryInPackString);
                return countCarryInPack;
            } catch (NumberFormatException e) {
                log.error("CountInPack is not an integer: " + doc.location(), e);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Unit getUnitCarry(Document doc) {
        Unit unitCarry;
        try {
            String unitCarryString = doc
                    .getElementsByClass(config.getCarryCountInPackParent())
                    .select(config.getCarryCountInPackChild())
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCarryCountInPackClass()).get(config.getCarryCountInPackIndex())
                    .text();
            unitCarryString = StringUtil.trimIfNotNull(unitCarryString);
            unitCarry = unitMessageInSequenceParserService.findUnitThroughMessagesSurroundedBraces(unitCarryString, getLocale());
        } catch (Exception e) {
            return null;
        }
        return unitCarry;
    }

    private Dimension getDimension(Document doc) {
        try {
            String dimensionString = doc
                    .getElementsByClass(config.getDimensionParent())
                    .select(config.getDimensionChild())
                    .select("div:containsOwn(" + config.getDimensionDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getDimensionDivContains1() + ")").first().parent()
                    .select(config.getDimensionValueClass()).get(config.getDimensionValueIndex())
                    .text();
            try {
                dimensionString = StringUtil.trimIfNotNull(dimensionString);
                Dimension dimension = dimensionParserService.parseXspliterMmToMm(dimensionString);
                return dimension;
            } catch (Exception e) {
                log.error("Dimension are not numbers: " + doc.location(), e);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getCountInPack(Document doc) {
        try {
            String countInPackString = doc
                    .getElementsByClass(config.getCountInPackParent())
                    .select(config.getCountInPackChild())
                    .select("div:containsOwn(" + config.getCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCountInPackValueClass()).get(config.getCountInPackValueIndex())
                    .text();
            try {
                countInPackString = StringUtil.trimIfNotNull(countInPackString);
                Integer countInPack = Integer.valueOf(countInPackString);
                return countInPack;
            } catch (NumberFormatException e) {
                log.error("Count in pack is not an integer: " + doc.location(), e);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Unit getUnit(Document doc) {
        try {
            String unitString = doc
                    .getElementsByClass(config.getCountInPackParent())
                    .select(config.getCountInPackChild())
                    .select("div:containsOwn(" + config.getCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCountInPackClass()).get(config.getCountInPackIndex())
                    .text();
            unitString = StringUtil.trimIfNotNull(unitString);
            Unit unit = unitMessageInSequenceParserService.findUnitThroughMessagesSurroundedBraces(unitString, getLocale());
            return unit;
        } catch (Exception e) {
            return null;
        }
    }

    private String getName(Document doc) {
        try {
            String name = doc
                    .getElementsByClass(config.getNameParent())
                    .select(config.getNameChild())
                    .get(config.getNameIndexElement())
                    .text();
            name = StringUtil.trimIfNotNull(name);
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    private String getCode(Document doc) {
        try {
            String code = doc
                    .getElementsByClass(config.getCodeParent())
                    .select(config.getCodeChild())
                    .get(config.getCodeIndexElement())
                    .text();
            code = StringUtil.trimIfNotNull(code);
            return code;
        } catch (Exception e) {
            return null;
        }
    }

    private String getVendorCode(Document doc) {
        try {
            String vendorCode = doc
                    .getElementsByClass(config.getVendorCodeParent())
                    .select(config.getVendorCodeChild())
                    .get(config.getVendorCodeIndexElement())
                    .text();
            vendorCode = StringUtil.trimIfNotNull(vendorCode);
            return vendorCode;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List<Category> getCategories(Document doc) throws Exception {
        List<Category> categories = new ArrayList<>();
        Elements elems = doc.getElementsByClass(config.getCategoriesParent()).select(config.getCategoriesChild());
        for (Element e : elems) {
            String categoryName = e.text();
            categoryName = StringUtil.trimIfNotNull(categoryName);
            Category category = new Category(categoryName);
            categories.add(category);
        }
        int exlLast = 1;
        categories = categories.subList(config.getCategoriesStartWithIndex(), categories.size() - exlLast);
        return categories;
    }

    @Override
    public List<Property> getProperties(Document doc) {
        List<Property> properties = new ArrayList<>();
        Elements elems = doc
                .getElementsByClass(config.getPropertiesParent())
                .select(config.getPropertiesChild())
                .select("div:containsOwn(" + config.getPropertiesDivContains() + ")").first().parent()
                .select(config.getPropertiesClass());
        for (Element e : elems) {
            String name = e.text();
            name = StringUtil.trimIfNotNull(name);
            Property p = new Property();
            p.setName(name);
            properties.add(p);
        }
        return properties;
    }

    @Override
    public List<String> getPropertiesValue(Document doc) {
        List<String> filtersValue = new ArrayList<>();
        Elements elems = doc
                .getElementsByClass(config.getPropertiesParent())
                .select(config.getPropertiesChild())
                .select("div:containsOwn(" + config.getPropertiesDivContains() + ")").first().parent()
                .select(config.getPropertiesValueClass());
        for (Element e : elems) {
            String value = e.text();
            value = StringUtil.trimIfNotNull(value);
            filtersValue.add(value);
        }
        return filtersValue;
    }

    @Override
    public List<String> getImagesUrl(Document doc) {
        List<String> urls = new ArrayList<>();
        Elements elems = doc
                .select(config.getImageParent())
                .select(config.getImageChild());
        for (Element e : elems) {
            String url = e.attr(config.getImageAttribute());
            url = StringUtil.trimIfNotNull(url);
            url = StringUtil.renameToNullIfContains(url, config.getDefaultPictureFileName());
            urls.add(url);
        }
        return urls;
    }

    @Override
    public String getUrl() {
        return config.getUrl();
    }

    @Override
    public Locale getLocale() {
        return config.getLocale();
    }

}