package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LowerCaseCategoryPathAdjusterService implements ICategoryPathAdjuster {

    @Override
    public void adjustPaths(List<Category> categories) throws Exception {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        for (Category category : categories) {
            String path = category.getPath();
            if (path != null) {
                category.setPath(path.toLowerCase());
            }
        }
    }
}
