package by.attrade.service.jsoup;

import by.attrade.domain.Category;
import by.attrade.domain.Product;
import by.attrade.domain.Property;
import by.attrade.service.jsoup.extractor.ProductJsoupS3RuExtractor;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductJsoupS3RuExtractorServiceIT {
    @Autowired
    private ProductJsoupS3RuExtractor service;
    @Autowired
    private JsoupDocService jsoupDocService;
    private String url = "http://www.s3.ru/ru/catalogue/fotohimiya/6802102-kodak-c-41-40-l-proyavitel-6800932-60_49.html";
    private Document doc;

    @Before
    public void init() throws IOException {
        doc = jsoupDocService.getJsoupDoc(url);
    }

    @Test
    public void getProduct() throws Exception {
        Product product = service.getProduct(doc);
    }

    @Test
    public void getCategories() throws Exception {
        List<Category> categories = service.getCategories(doc);
    }

    @Test
    public void getFilters() throws Exception {
        List<Property> properties = service.getProperties(doc);
    }

    @Test
    public void getFiltersValue() throws Exception {
        List<String> values = service.getPropertiesValue(doc);
    }

    @Test
    public void getImagesUrl() throws Exception {
        List<String> urls = service.getImagesUrl(doc);
    }

}