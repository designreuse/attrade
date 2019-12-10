package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import by.attrade.service.LanguageDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryPathByNameAdjusterService {
    @Autowired
    private TranslatorCategoryPathByNameAdjusterService translatorCategoryPathByNameAdjusterService;
    @Autowired
    private ReplacePunctuationMarksCategoryPathAdjusterService replacePunctuationMarksCategoryPathAdjusterService;
    @Autowired
    private SpaceToHyphenCategoryPathAdjusterService spaceToHyphenCategoryPathAdjusterService;
    @Autowired
    private LowerCaseCategoryPathAdjusterService lowerCaseCategoryPathAdjusterService;
    @Autowired
    private LanguageDetectionService languageDetectionService;


    public void adjustPaths(List<Category> categories, String langFrom, String langTo) throws Exception {
        if (categories == null || categories.isEmpty()){
            return;
        }
        categories = getCategoriesWithNullPath(categories, langFrom);
        translatorCategoryPathByNameAdjusterService.adjustPaths(categories, langFrom, langTo);
        ICategoryPathAdjuster.chainAdjustPaths(categories,
                replacePunctuationMarksCategoryPathAdjusterService,
                spaceToHyphenCategoryPathAdjusterService,
                lowerCaseCategoryPathAdjusterService);
    }

    private List<Category> getCategoriesWithNullPath(List<Category> categories, String langFrom) {
        return categories.stream().filter(x-> x!=null && (x.getPath()==null || languageDetectionService.detect(x.getPath()).equals(langFrom))).collect(Collectors.toList());
    }
}
