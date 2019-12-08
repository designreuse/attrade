package by.attrade.service;

import by.attrade.domain.ExtractorError;
import by.attrade.excel.ExcelCellReader;
import by.attrade.service.jsoup.extractor.ProductJsoupS3RuExtractor;
import by.attrade.service.jsoup.extractor.ProductJsoupTexenergoRuExtractor;
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
public class ProductExtractorServiceIT {
    private String domain = "http://www.s3.ru/ru";
    private String s3Url = "http://www.s3.ru/ru/catalogue/fotohimiya/6802102-kodak-c-41-40-l-proyavitel-6800932-60_49.html";
    private String texenergoUrl = "https://www.texenergo.ru/catalog/item.html/te00242213";
    private int iRowStart = 0;
    private int iRowEnd = 11353;
    //    private int iRowEnd = 1000;
    private int iColumn = 2;
    private String path = "d:\\attrade\\catalogue_s3.ru.xls";
    @Autowired
    private ProductExtractorService service;
    @Autowired
    private ExcelCellReader excelCellReader;
    @Autowired
    private ProductJsoupS3RuExtractor s3RuExtractor;
    @Autowired
    private ProductJsoupTexenergoRuExtractor texenergoRuExtractor;

    private List<String> urls;

    @Before
    public void init() throws IOException {
        urls = excelCellReader.getStringColumnAsStringURLList(iRowStart, iRowEnd, iColumn, path);
    }

    @Test
    public void saveProducts() throws Exception {
        ExtractorError extractorError = service.saveProductsIfNotExistsByCodeAndSaveErrors(s3RuExtractor, urls, null);
        extractorError.getUrls().forEach(System.out::println);
    }
    @Test
    public void saveS3DomainProducts() throws Exception {
        ExtractorError extractorError = service.saveProductsIfNotExistsByCodeAndSaveErrors(s3RuExtractor, null);
        extractorError.getUrls().forEach(System.out::println);
    }

    @Test
    public void saveS3Product() throws Exception {
        service.saveProductIfNotExistsByCode(s3RuExtractor, s3Url, null);
    }
    @Test
    public void saveTexenergoProduct() throws Exception {
        service.saveProductIfNotExistsByCode(texenergoRuExtractor, texenergoUrl, null);
    }
    @Test
    public void saveTexenergoDomainProducts() throws Exception {
        ExtractorError extractorError = service.saveProductsIfNotExistsByCodeAndSaveErrors(texenergoRuExtractor, null);
        extractorError.getUrls().forEach(System.out::println);
    }
}