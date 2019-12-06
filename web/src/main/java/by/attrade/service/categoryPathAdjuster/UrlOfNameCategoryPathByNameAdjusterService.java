package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import by.attrade.service.translit.CyrillicLatinTranslitService;
import by.attrade.service.translit.SpaceHyphenTranslitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlOfNameCategoryPathByNameAdjusterService implements ICategoryPathAdjuster {
    @Autowired
    private CyrillicLatinTranslitService cyrillicLatinTranslitService;
    @Autowired
    private SpaceHyphenTranslitService spaceHyphenTranslitService;


    @Override
    public void adjustPaths(List<Category> categories) throws Exception {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (Category category : categories) {
            String path = cyrillicLatinTranslitService.toTranslit(category.getName());
            path = spaceHyphenTranslitService.toTranslit(path);
            category.setPath(path.toLowerCase());
        }
    }
}
