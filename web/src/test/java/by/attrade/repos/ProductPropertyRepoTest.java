package by.attrade.repos;

import by.attrade.domain.Category;
import by.attrade.domain.Property;
import by.attrade.service.CategoryHierarchyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPropertyRepoTest {
    @Autowired
    private ProductPropertyRepo productPropertyRepo;
    @Autowired
    private CategoryHierarchyService categoryHierarchyService;
    private String path;

    @Before
    public void init(){
        path = "wedding";
    }

    @Test
    public void getPropertyPropertyData() throws Exception {
        Category category = categoryHierarchyService.getCategoryByPath(path);
        List<Property> properties = category.getProperties();
        productPropertyRepo.getPropertyPropertyData(properties);
    }

}