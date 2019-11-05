package by.attrade.service.search;

import by.attrade.domain.Category;
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
public class CategorySearchServiceIT {
    @Autowired
    private CategorySearchService service;

    private Pageable pageable = PageRequest.of(1, 50);
    private String textMore3 = "вето свет";
    private String textTo3 = "вет";

    @Test
    public void searchCategoryExcessMinGramSize() throws Exception {
        LocalTime start = LocalTime.now();
        List<Category> category = service.searchCategoryExcessMinGramSize(textMore3, pageable);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        category.forEach(System.out::println);
    }
    @Test
    public void searchCategoryInsideMinGramSize() throws Exception {
        LocalTime start = LocalTime.now();
        List<Category> category = service.searchCategoryInsideMinGramSize(textTo3, pageable);
        System.out.println("Duration: " + Duration.between(start, LocalTime.now()));
        category.forEach(System.out::println);
    }
}