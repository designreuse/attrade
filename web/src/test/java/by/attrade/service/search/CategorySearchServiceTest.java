package by.attrade.service.search;

import by.attrade.domain.Category;
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
public class CategorySearchServiceTest {
    @Autowired
    private CategorySearchService service;
    private String textMore3 = "вето свет";
    private String textTo3 = "вет";

    @Test
    public void searchCategoryByMoreThan3Char() throws Exception {
        LocalTime start = LocalTime.now();
        List<Category> category = service.searchCategoryByMoreThan3Char(textMore3);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        category.forEach(System.out::println);
    }
    @Test
    public void searchCategoryBy3CharIncl() throws Exception {
        LocalTime start = LocalTime.now();
        List<Category> category = service.searchCategoryByLessThan3CharIncl(textTo3);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        category.forEach(System.out::println);
    }
}