package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import by.attrade.service.translit.SpaceHyphenTranslitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpaceToHyphenCategoryPathAdjusterService implements ICategoryPathAdjuster {
    @Autowired
    private SpaceHyphenTranslitService spaceHyphenTranslitService;

    @Override
    public void adjustPaths(List<Category> categories) throws Exception {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (Category category : categories) {
            String path = category.getPath();
            if (path != null) {
                path = spaceHyphenTranslitService.toTranslit(path);
                category.setPath(path);
            }
        }
    }
}
