package by.attrade.service.jsoup;

import by.attrade.domain.Category;
import by.attrade.domain.Property;
import by.attrade.domain.Product;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface IProductExtractor {
    boolean isContainProduct(Document doc);
    Product getProduct(Document doc) throws IOException;

    List<Category> getCategories(Document doc) throws Exception;

    List<Property> getProperties(Document doc) throws IOException;

    List<String> getPropertiesValue(Document doc) throws IOException;

    List<String> getImagesUrl(Document doc) throws IOException;

    String getUrl();

    Locale getLocale();
}
