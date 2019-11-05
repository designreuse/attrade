package by.attrade.service.search;

import by.attrade.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSearchServiceIT {
    @Autowired
    private ProductSearchService service;
    private Pageable pageable = PageRequest.of(1, 50);
    private String textMore3 = "вето свет";
    private String textTo3 = "вет";

    @Test
    public void searchProductExcessMinGramSize() throws Exception {
        LocalTime start = LocalTime.now();
        List<Product> products = service.searchProductExcessMinGramSize(textMore3, pageable);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        products.forEach(System.out::println);

    }
    @Test
    public void searchProductInsideMinGramSize(){
        LocalTime start = LocalTime.now();
        List<Product> products = service.searchProductInsideMinGramSize(textTo3, pageable);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        products.forEach(System.out::println);
    }
}