package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public void adjustPaths(List<Category> categories, String langFrom, String langTo) throws Exception {
        translatorCategoryPathByNameAdjusterService.adjustPaths(categories, langFrom, langTo);
        ICategoryPathAdjuster.chainAdjustPaths(categories,
                replacePunctuationMarksCategoryPathAdjusterService,
                spaceToHyphenCategoryPathAdjusterService,
                lowerCaseCategoryPathAdjusterService);
    }
}
