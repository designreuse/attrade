package by.attrade.service.jsoup.extractor;

import by.attrade.config.JsoupS3RuProductConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Product;
import by.attrade.domain.Property;
import by.attrade.service.jsoup.IProductExtractor;
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
    public boolean isContainProduct(Document doc) {
        String code = getCode(doc);
        String name = getName(doc);
        return code != null && name != null;
    }

    @Override
    public Product getProduct(Document doc) {
        String code = getCode(doc);
        String name = getName(doc);
        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        return product;
    }

    private String getName(Document doc) {
        try {
            String name = doc.select(config.getNameParent()).select(config.getNameChild()).text();
            name = StringUtil.trimIfNotNull(name);
            return name;
        } catch (Exception e) {
            return null;
        }
    }

    private String getCode(Document doc) {
        try {
            String code = doc.select(config.getCodeParent()).select(config.getCodeChild()).get(config.getCodeIndexElement()).text();
            code = StringUtil.trimIfNotNull(code);
            return code;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Category> getCategories(Document doc) throws Exception {
        List<Category> categories = new ArrayList<>();
        Elements elems = doc.select(config.getCategoriesParent()).select(config.getCategoriesChild());
        for (Element e : elems) {
            String categoryName = e.text();
            categoryName = StringUtil.trimIfNotNull(categoryName);
            Category category = new Category(categoryName);
            categories.add(category);
        }
        categories = categories.subList(config.getCategoriesStartWithIndex(), categories.size());
        return categories;
    }

    @Override
    public List<Property> getProperties(Document doc) {
        List<Property> properties = new ArrayList<>();
        Elements elems = doc.select(config.getPropertiesParent()).select(config.getPropertiesChild());
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
        return properties;
    }

    @Override
    public List<String> getPropertiesValue(Document doc) {
        List<String> filtersValue = new ArrayList<>();
        Elements elems = doc.select(config.getPropertiesParent()).select(config.getPropertiesChild());
        boolean rotate = false;
        for (Element e : elems) {
            if (rotate) {
                String value = e.text();
                value = StringUtil.trimIfNotNull(value);
                filtersValue.add(value);
            }
            rotate = !rotate;
        }
        return filtersValue;
    }

    @Override
    public List<String> getImagesUrl(Document doc) {
        List<String> urls = new ArrayList<>();
        Elements elems = doc.select(config.getImageParent()).select(config.getImageChild());
        for (Element e : elems) {
            String url = e.absUrl("src");
            url = StringUtil.trimIfNotNull(url);
            url = StringUtil.renameToNullIfContains(url, config.getDefaultPictureFileName());
            urls.add(url);
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
    public Locale getLocale(){return config.getLocale();}
}
