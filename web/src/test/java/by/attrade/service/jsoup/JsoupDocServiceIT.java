package by.attrade.service.jsoup;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsoupDocServiceIT {
    @Autowired
    private JsoupDocService jsoupDocService;
    String url = "https://www.texenergo.ru/catalog/list.html/433310";
    private boolean extractByFileLoading = true;

    @Test
    public void getAllHrefNotPdfNotImage() throws Exception {
        Document doc = jsoupDocService.getJsoupDoc(url);
        System.out.println(doc.html());
        Stream<String> allHrefNotPdfNotImage = jsoupDocService.getAllHrefNotPdfNotImage(doc, extractByFileLoading);
        allHrefNotPdfNotImage.forEach(System.out::println);
    }

}