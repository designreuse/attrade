package by.attrade.service.jsoup.extractor;

import by.attrade.config.JsoupTexenergoRuProductConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Dimension;
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
    public String getCode(Document doc) {
        String code = null;
        try {
            code = doc
                    .getElementsByClass(config.getCodeParent())
                    .select(config.getCodeChild())
                    .get(config.getCodeIndexElement())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find code: " + doc.location(), e.getMessage());
        }
        if (code != null) {
            code = StringUtil.removeSpaces(code);
        }
        return code;
    }

    @Override
    public String getVendorCode(Document doc) {
        String vendorCode = null;
        try {
            vendorCode = doc
                    .getElementsByClass(config.getVendorCodeParent())
                    .select(config.getVendorCodeChild())
                    .get(config.getVendorCodeIndexElement())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find vendor code: " + doc.location(), e.getMessage());
        }
        if (vendorCode != null) {
            vendorCode = StringUtil.removeSpaces(vendorCode);
        }
        return vendorCode;
    }

    @Override
    public String getName(Document doc) {
        String name = null;
        try {
            name = doc
                    .getElementsByClass(config.getNameParent())
                    .select(config.getNameChild())
                    .get(config.getNameIndexElement())
                    .text();
        } catch (Exception e) {
            log.info("Cannot read name: " + doc.location(), e.getMessage());
        }
        if (name != null) {
            name = StringUtil.trimIfNotNull(name);
        }
        return name;

    }

    @Override
    public Unit getUnit(Document doc) {
        String unitString = null;
        Unit unit = null;
        try {
            unitString = doc
                    .getElementsByClass(config.getCountParent())
                    .select(config.getCountChild())
                    .select("div:containsOwn(" + config.getCountDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountDivContains1() + ")").first().parent()
                    .select(config.getCountClass()).get(config.getCountIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find unit: " + doc.location(), e.getMessage());
        }
        if (unitString != null) {
            unitString = StringUtil.removeSpaces(unitString);
            unit = unitMessageInSequenceParserService.findUnitThroughMessagesSurroundedBraces(unitString, getLocale());
        }
        return unit;
    }

    @Override
    public Integer getCount(Document doc) {
        String countString = null;
        Integer countPack = null;
        try {
            countString = doc
                    .getElementsByClass(config.getCountParent())
                    .select(config.getCountChild())
                    .select("div:containsOwn(" + config.getCountDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountDivContains1() + ")").first().parent()
                    .select(config.getCountValueClass()).get(config.getCountValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find count: " + doc.location(), e.getMessage());
        }
        if (countString != null) {
            try {
                countString = StringUtil.removeSpaces(countString);
                countPack = Integer.valueOf(countString);
            } catch (NumberFormatException e) {
                log.error("CountInPack is not an integer: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return countPack;
    }

    @Override
    public Dimension getDimension(Document doc) {
        String dimensionString = null;
        Dimension dimension = null;
        try {
            dimensionString = doc
                    .getElementsByClass(config.getDimensionParent())
                    .select(config.getDimensionChild())
                    .select("div:containsOwn(" + config.getDimensionDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getDimensionDivContains1() + ")").first().parent()
                    .select(config.getDimensionValueClass()).get(config.getDimensionValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find dimension: " + doc.location(), e.getMessage());
        }
        if (dimensionString != null) {
            try {
                dimensionString = StringUtil.removeSpaces(dimensionString);
                dimension = dimensionParserService.parseXspliterMmToMm(dimensionString);
            } catch (NumberFormatException e) {
                log.error("Dimension is not a double: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return dimension;
    }

    @Override
    public Double getWeight(Document doc) {
        String weightString = null;
        Double weight = null;
        try {
            weightString = doc
                    .getElementsByClass(config.getWeightParent())
                    .select(config.getWeightChild())
                    .select("div:containsOwn(" + config.getWeightDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getWeightDivContains1() + ")").first().parent()
                    .select(config.getWeightValueClass()).get(config.getWeightValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find weight: " + doc.location(), e.getMessage());
        }
        if (weightString != null) {
            weightString = StringUtil.removeSpaces(weightString);
            try {
                weight = Double.parseDouble(weightString);
            } catch (NumberFormatException e) {
                log.error("Weight is not a double: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return weight;
    }

    @Override
    public Unit getUnitInPack(Document doc) {
        String unitPackString = null;
        Unit unitPack = null;
        try {
            unitPackString = doc
                    .getElementsByClass(config.getCountInPackParent())
                    .select(config.getCountInPackChild())
                    .select("div:containsOwn(" + config.getCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCountInPackClass()).get(config.getCountInPackIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find unit pack: " + doc.location(), e.getMessage());
        }
        if (unitPackString != null) {
            unitPackString = StringUtil.removeSpaces(unitPackString);
            unitPack = unitMessageInSequenceParserService.findUnitThroughMessagesSurroundedBraces(unitPackString, getLocale());
        }
        return unitPack;
    }

    @Override
    public Integer getCountInPack(Document doc) {
        String countInPackString = null;
        Integer countInPack = null;
        try {
            countInPackString = doc
                    .getElementsByClass(config.getCountInPackParent())
                    .select(config.getCountInPackChild())
                    .select("div:containsOwn(" + config.getCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCountInPackValueClass()).get(config.getCountInPackValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find count in pack: " + doc.location(), e.getMessage());
        }
        if (countInPackString != null) {
            try {
                countInPackString = StringUtil.removeSpaces(countInPackString);
                countInPack = Integer.valueOf(countInPackString);
            } catch (NumberFormatException e) {
                log.error("Count in pack is not an integer: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return countInPack;
    }

    @Override
    public Dimension getDimensionPack(Document doc) {
        String dimensionPackString = null;
        Dimension dimensionPack = null;
        try {
            dimensionPackString = doc
                    .getElementsByClass(config.getDimensionPackParent())
                    .select(config.getDimensionPackChild())
                    .select("div:containsOwn(" + config.getDimensionPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getDimensionPackDivContains1() + ")").first().parent()
                    .select(config.getDimensionPackValueClass()).get(config.getDimensionPackValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find dimension pack: " + doc.location(), e.getMessage());
        }
        if (dimensionPackString != null) {
            try {
                dimensionPackString = StringUtil.removeSpaces(dimensionPackString);
                dimensionPack = dimensionParserService.parseXspliterMmToMm(dimensionPackString);

            } catch (Exception e) {
                log.error("Dimension are not numbers: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return dimensionPack;
    }

    @Override
    public Double getWeightPack(Document doc) {
        String weightPackString = null;
        Double weightPack = null;
        try {
            weightPackString = doc
                    .getElementsByClass(config.getWeightPackParent())
                    .select(config.getWeightPackChild())
                    .select("div:containsOwn(" + config.getWeightPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getWeightPackDivContains1() + ")").first().parent()
                    .select(config.getWeightPackValueClass()).get(config.getWeightPackValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find weight pack: " + doc.location(), e.getMessage());
        }
        if (weightPackString != null) {
            weightPackString = StringUtil.removeSpaces(weightPackString);
            try {
                weightPack = Double.parseDouble(weightPackString);
            } catch (NumberFormatException e) {
                log.error("Weight is not a double: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return weightPack;

    }

    @Override
    public Unit getUnitCarry(Document doc) {
        String unitCarryString = null;
        Unit unitCarry = null;
        try {
            unitCarryString = doc
                    .getElementsByClass(config.getCarryCountInPackParent())
                    .select(config.getCarryCountInPackChild())
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCarryCountInPackClass()).get(config.getCarryCountInPackIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find unit carry: " + doc.location(), e.getMessage());
        }
        if (unitCarryString != null) {
            unitCarryString = StringUtil.removeSpaces(unitCarryString);
            unitCarry = unitMessageInSequenceParserService.findUnitThroughMessagesSurroundedBraces(unitCarryString, getLocale());
        }
        return unitCarry;

    }

    @Override
    public Integer getCountInPackCarry(Document doc) {
        String countCarryInPackString = null;
        Integer countCarryInPack = null;
        try {
            countCarryInPackString = doc
                    .getElementsByClass(config.getCarryCountInPackParent())
                    .select(config.getCarryCountInPackChild())
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryCountInPackDivContains1() + ")").first().parent()
                    .select(config.getCarryCountInPackValueClass()).get(config.getCarryCountInPackValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find count in pack carry: " + doc.location(), e.getMessage());
        }
        if (countCarryInPackString != null) {
            try {
                countCarryInPackString = StringUtil.removeSpaces(countCarryInPackString);
                countCarryInPack = Integer.valueOf(countCarryInPackString);
            } catch (NumberFormatException e) {
                log.error("CountInPack is not an integer: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return countCarryInPack;
    }

    @Override
    public Dimension getDimensionCarry(Document doc) {
        String dimensionCarryString = null;
        Dimension dimensionCarry = null;
        try {
            dimensionCarryString = doc
                    .getElementsByClass(config.getCarryDimensionParent())
                    .select(config.getCarryDimensionChild())
                    .select("div:containsOwn(" + config.getCarryDimensionDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryDimensionDivContains1() + ")").first().parent()
                    .select(config.getCarryDimensionValueClass()).get(config.getCarryDimensionValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find dimension carry: " + doc.location(), e.getMessage());
        }
        if (dimensionCarryString != null) {
            try {
                dimensionCarryString = StringUtil.removeSpaces(dimensionCarryString);
                dimensionCarry = dimensionParserService.parseXspliterMmToMm(dimensionCarryString);
            } catch (NumberFormatException e) {
                log.error("Dimension is not a double: " + doc.location(), e.getMessage());
                return null;
            }
        }
        return dimensionCarry;
    }

    @Override
    public Double getWeightCarry(Document doc) {
        String weightCarryString = null;
        Double weightCarry = null;
        try {
            weightCarryString = doc
                    .getElementsByClass(config.getCarryWeightParent())
                    .select(config.getCarryWeightChild())
                    .select("div:containsOwn(" + config.getCarryWeightDivContains() + ")").first().parent()
                    .select("div:containsOwn(" + config.getCarryWeightDivContains1() + ")").first().parent()
                    .select(config.getCarryWeightValueClass()).get(config.getCarryWeightValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find weight carry: " + doc.location(), e.getMessage());
        }
        if (weightCarryString != null) {
            weightCarryString = StringUtil.removeSpaces(weightCarryString);
            try {
                weightCarry = Double.parseDouble(weightCarryString);
            } catch (NumberFormatException e) {
                log.error("WeightCarry is not a double: " + doc.location(), e.getMessage());
            }
        }
        return weightCarry;

    }

    @Override
    public String getBarcode(Document doc) {
        String barcode = null;
        try {
            barcode = doc
                    .getElementsByClass(config.getBarcodeParent())
                    .select(config.getBarcodeChild())
                    .select("div:containsOwn(" + config.getBarcodeDivContains() + ")").first().parent()
                    .select(config.getBarcodeValueClass()).get(config.getBarcodeValueIndex())
                    .text();
        } catch (Exception e) {
            log.info("Cannot find barcode: " + doc.location(), e.getMessage());
        }
        if (barcode != null) {
            barcode = StringUtil.removeSpaces(barcode);
        }
        return barcode;
    }

    @Override
    public String getDescription(Document doc) {
        String description = null;
        doc = doc.clone();
        try {
            doc.select(config.getDescriptionImageSelectForWrap()).wrap(config.getDescriptionImageWrap());
            doc.select(config.getDescriptionImageSelectForUnwrap()).unwrap();
            doc.select(config.getDescriptionImageSelectForRemove()).remove();
            doc.select(config.getDescriptionImageSelectContainsForRemove()).remove();
            Elements elems = doc
                    .getElementsByClass(config.getDescriptionGetElementsByClass());

            description = elems.html();
        } catch (Exception e) {
            log.info("Cannot find description: " + doc.location(), e.getMessage());
        }
        return description;
    }


    @Override
    public List<Category> getCategories(Document doc) {
        List<Category> categories = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .getElementsByClass(config.getCategoriesParent())
                    .select(config.getCategoriesChild());
        } catch (Exception e) {
            log.info("Cannot find categories: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String categoryName = e.text();
                categoryName = StringUtil.trimIfNotNull(categoryName);
                Category category = new Category(categoryName);
                categories.add(category);
            }
            int exlLast = 1;
            categories = categories.subList(config.getCategoriesStartWithIndex(), categories.size() - exlLast);
        }
        return categories;
    }

    @Override
    public List<Property> getProperties(Document doc) {
        List<Property> properties = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .getElementsByClass(config.getPropertiesParent())
                    .select(config.getPropertiesChild())
                    .select("div:containsOwn(" + config.getPropertiesDivContains() + ")").first().parent()
                    .select(config.getPropertiesClass());
        } catch (Exception e) {
            log.info("Cannot find properties: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String name = e.text();
                name = StringUtil.trimIfNotNull(name);
                Property p = new Property();
                p.setName(name);
                properties.add(p);
            }
        }
        return properties;
    }

    @Override
    public List<String> getPropertyValues(Document doc) {
        List<String> propertyValues = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .getElementsByClass(config.getPropertiesParent())
                    .select(config.getPropertiesChild())
                    .select("div:containsOwn(" + config.getPropertiesDivContains() + ")").first().parent()
                    .select(config.getPropertiesValueClass());
        } catch (Exception e) {
            log.info("Cannot find property values: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String value = e.text();
                value = StringUtil.trimIfNotNull(value);
                propertyValues.add(value);
            }
        }
        return propertyValues;
    }

    @Override
    public List<String> getImagesUrl(Document doc) {
        List<String> urls = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .getElementsByClass(config.getImageParent())
                    .select(config.getImageSelect());
        } catch (Exception e) {
            log.info("Cannot find images url: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String url = e.absUrl(config.getImageUrl());
                url = StringUtil.trimIfNotNull(url);
                url = StringUtil.renameToNullIfContains(url, config.getDefaultPictureFileName());
                urls.add(url);
            }
        }
        return urls;
    }

    @Override
    public List<String> getDescriptionImagesUrl(Document doc) {
        doc = doc.clone();
        List<String> urls = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .getElementsByClass(config.getDescriptionGetElementsByClass())
                    .select(config.getDescriptionImageSelect());
        } catch (Exception e) {
            log.info("Cannot find description images url: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String url = e.absUrl(config.getDescriptionImageUrl());
                url = StringUtil.trimIfNotNull(url);
                url = StringUtil.renameToNullIfContains(url, config.getDefaultPictureFileName());
                urls.add(url);
            }
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

    @Override
    public boolean isHrefExtractsByFileLoading() {
        return config.isExtractByFileLoading();
    }
}
