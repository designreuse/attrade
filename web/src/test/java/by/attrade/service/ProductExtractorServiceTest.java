package by.attrade.service;

import by.attrade.excel.ExcelCellReader;
import by.attrade.service.jsoup.extractor.ProductJsoupS3RuExtractor;
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
public class ProductExtractorServiceTest {
    private String url = "http://www.s3.ru/ru/catalogue/fotohimiya/6802102-kodak-c-41-40-l-proyavitel-6800932-60_49.html";
    private int iRowStart = 0;
//    private int iRowEnd = 11353;
    private int iRowEnd = 100;
    private int iColumn = 2;
    private String path = "d:\\attrade\\catalogue_s3.ru.xls";
    @Autowired
    private ProductExtractorService service;
    @Autowired
    private ExcelCellReader excelCellReader;
    @Autowired
    private ProductJsoupS3RuExtractor extractor;

    private List<String> urls;

    @Before
    public void init() throws IOException {
        urls = excelCellReader.getStringColumnAsStringURLList(iRowStart,iRowEnd,iColumn,path);
    }

    @Test
    public void saveProducts() throws Exception {
        service.saveProductsIfNotExistsByCode(extractor, urls);
    }

    @Test
    public void saveProduct() throws Exception {
        service.saveProductIfNotExistsByCode(extractor, url);
    }

}