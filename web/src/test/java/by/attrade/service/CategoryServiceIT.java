package by.attrade.service;

import by.attrade.config.PictureMediaConfig;
import by.attrade.domain.Category;
import by.attrade.service.categoryPathExtractor.UrlOfNameCategoryPathExtractorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceIT {
    @Autowired
    private PictureMediaConfig pictureMediaConfig;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UrlOfNameCategoryPathExtractorService extractor;
    @Test
    public void updatePaths() throws Exception {
        categoryService.updatePaths(extractor, 100);
    }
    @Test
    public void updatePictures(){
        String pictureFileName = pictureMediaConfig.getDefaultPictureFileName();
        List<Category> all = categoryService.findAll();
        all.forEach(x->x.setPicture(pictureFileName));
        categoryService.saveAll(all);
    }
}