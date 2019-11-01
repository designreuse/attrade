package by.attrade.service.jsoup.extractor;

import by.attrade.config.JsoupS3ruProductConfig;
import by.attrade.domain.Category;
import by.attrade.domain.Product;
import by.attrade.domain.Property;
import by.attrade.service.jsoup.IProductExtractor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductJsoupS3RuExtractor implements IProductExtractor {
    public static final String MAIN_URL = "http://www.s3.ru";

    @Autowired
    private JsoupS3ruProductConfig config;

    @Override
    public Product getProduct(Document doc) {
        String code = doc.select(config.getCodeParent()).select(config.getCodeChild()).get(config.getCodeIndexElement()).text();
        String name = doc.select(config.getNameParent()).select(config.getNameChild()).text();
        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        return product;
    }

    @Override
    public List<Category> getCategories(Document doc) {
        List<Category> categories = new ArrayList<>();
        Elements elems = doc.select(config.getCategoriesParent()).select(config.getCategoriesChild());
        for (Element e : elems) {
            String categoryName = e.text();
            categories.add(new Category(categoryName));
        }
        categories = categories.subList(config.getCategoriesStartWithIndex(), categories.size());
        return categories;
    }

    @Override
    public List<Property> getProperties(Document doc) {
        List<Property> properties = new ArrayList<>();
        Elements elems = doc.select(config.getFiltersParent()).select(config.getFiltersChild());
        boolean rotate = true;
        for (Element e : elems) {
            if (rotate) {
                String name = e.text();
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
        Elements elems = doc.select(config.getFiltersParent()).select(config.getFiltersChild());
        boolean rotate = false;
        for (Element e : elems) {
            if (rotate) {
                filtersValue.add(e.text());
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
            urls.add(e.absUrl("src"));
        }
        return urls;
    }

    @Override
    public String getMainUrl() {
        return MAIN_URL;
    }
}
