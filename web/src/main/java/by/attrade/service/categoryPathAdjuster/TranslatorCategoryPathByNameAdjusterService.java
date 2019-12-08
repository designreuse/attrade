package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import by.attrade.service.TranslatorGoogleUsingScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TranslatorCategoryPathByNameAdjusterService {
    private static final String SPLITOR = "||";
    @Autowired
    private TranslatorGoogleUsingScriptService translatorService;

    public void adjustPaths(List<Category> categories, String langFrom, String langTo) throws Exception {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(category.getName());
            sb.append(SPLITOR);
        }
        String text = translatorService.translate(langFrom, langTo, sb.toString());
        String[] paths = text.split(Pattern.quote(SPLITOR));
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setPath(paths[i].trim().toLowerCase());
        }
    }
}
