package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import by.attrade.service.TranslatorGoogleUsingScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TranslatorCategoryPathByNameAdjusterService implements ICategoryPathAdjuster {
    private static final String SPLITOR = ".!?";
    @Autowired
    private TranslatorGoogleUsingScriptService translatorService;

    @Override
    public void adjustPaths(List<Category> categories) throws Exception {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(category.getName());
            sb.append(SPLITOR);
        }
        String text = translatorService.translate("ru", "en", sb.toString());
        String[] paths = text.split(SPLITOR);
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setPath(paths[i].toLowerCase());
        }
    }
}
