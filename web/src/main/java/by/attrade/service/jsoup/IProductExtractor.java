package by.attrade.service.jsoup;

import by.attrade.domain.Category;
import by.attrade.domain.Property;
import by.attrade.domain.Product;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;

public interface IProductExtractor {
    Product getProduct(Document document) throws IOException;

    List<Category> getCategories(Document document) throws Exception;

    List<Property> getProperties(Document document) throws IOException;

    List<String> getPropertiesValue(Document document) throws IOException;

    List<String> getImagesUrl(Document document) throws IOException;

    String getUrl();
}
