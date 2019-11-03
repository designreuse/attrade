package by.attrade.service.search;

import by.attrade.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSearchServiceTest {
    @Autowired
    private ProductSearchService service;
    private String textMore3 = "вето свет";
    private String textTo3 = "вет";

    @Test
    public void searchProductByMoreThan3Char() throws Exception {
        LocalTime start = LocalTime.now();
        List<Product> products = service.searchProductByMoreThan3Char(textMore3);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        products.forEach(System.out::println);

    }
    @Test
    public void searchProductBy3CharIncl(){
        LocalTime start = LocalTime.now();
        List<Product> products = service.searchProductByLessThan3CharIncl(textTo3);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        products.forEach(System.out::println);
    }
}