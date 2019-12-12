package by.attrade.service.jsoup.extractor;

import by.attrade.config.JsoupS3RuProductConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Dimension;
import by.attrade.domain.Property;
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
public class ProductJsoupS3RuExtractor implements IProductExtractor {

    @Autowired
    private JsoupS3RuProductConfig config;

    @Override
    public String getVendorCode(Document doc) {
        return null;
    }

    @Override
    public String getName(Document doc) {
        try {
            String name = doc.select(config.getNameParent()).select(config.getNameChild()).text();
            name = StringUtil.trimIfNotNull(name);
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getCode(Document doc) {
        try {
            String code = doc.select(config.getCodeParent()).select(config.getCodeChild()).get(config.getCodeIndexElement()).text();
            code = StringUtil.trimIfNotNull(code);
            return code;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Unit getUnit(Document doc) {
        return null;
    }

    @Override
    public Integer getCount(Document doc) {
        return null;
    }

    @Override
    public Dimension getDimension(Document doc) {
        return null;
    }

    @Override
    public Double getWeight(Document doc) {
        return null;
    }

    @Override
    public Unit getUnitInPack(Document doc) {
        return null;
    }

    @Override
    public Integer getCountInPack(Document doc) {
        return null;
    }

    @Override
    public Dimension getDimensionPack(Document doc) {
        return null;
    }

    @Override
    public Double getWeightPack(Document doc) {
        return null;
    }

    @Override
    public Unit getUnitCarry(Document doc) {
        return null;
    }

    @Override
    public Integer getCountInPackCarry(Document doc) {
        return null;
    }

    @Override
    public Dimension getDimensionCarry(Document doc) {
        return null;
    }

    @Override
    public Double getWeightCarry(Document doc) {
        return null;
    }

    @Override
    public String getBarcode(Document doc) {
        return null;
    }

    @Override
    public String getDescription(Document doc) {
        return null;
    }

    @Override
    public List<Category> getCategories(Document doc) {
        List<Category> categories = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .select(config.getCategoriesParent())
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
            categories = categories.subList(config.getCategoriesStartWithIndex(), categories.size());
        }
        return categories;
    }

    @Override
    public List<Property> getProperties(Document doc) {
        List<Property> properties = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .select(config.getPropertiesParent())
                    .select(config.getPropertiesChild());
        } catch (Exception e) {
            log.info("Cannot find properties: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            boolean rotate = true;
            for (Element e : elems) {
                if (rotate) {
                    String name = e.text();
                    name = StringUtil.trimIfNotNull(name);
                    Property p = new Property();
                    p.setName(name);
                    properties.add(p);
                }
                rotate = !rotate;
            }
        }
        return properties;
    }

    @Override
    public List<String> getPropertyValues(Document doc) {
        List<String> filtersValue = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .select(config.getPropertiesParent())
                    .select(config.getPropertiesChild());
        } catch (Exception e) {
            log.info("Cannot find property values: " + doc.location(), e.getMessage());
        }
        boolean rotate = false;
        if (elems != null) {
            for (Element e : elems) {
                if (rotate) {
                    String value = e.text();
                    value = StringUtil.trimIfNotNull(value);
                    filtersValue.add(value);
                }
                rotate = !rotate;
            }
        }
        return filtersValue;
    }

    @Override
    public List<String> getImagesUrl(Document doc) {
        List<String> urls = new ArrayList<>();
        Elements elems = null;
        try {
            elems = doc
                    .select(config.getImageParent())
                    .select(config.getImageChild());
        } catch (Exception e) {
            log.info("Cannot find images url: " + doc.location(), e.getMessage());
        }
        if (elems != null) {
            for (Element e : elems) {
                String url = e.absUrl("src");
                url = StringUtil.trimIfNotNull(url);
                url = StringUtil.renameToNullIfContains(url, config.getDefaultPictureFileName());
                urls.add(url);
            }
        }
        return urls;
    }

    @Override
    public List<String> getDescriptionImagesUrl(Document doc) {
        return null;
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
