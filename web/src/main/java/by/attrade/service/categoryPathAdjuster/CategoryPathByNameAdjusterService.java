package by.attrade.service.categoryPathAdjuster;

import by.attrade.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryPathByNameAdjusterService implements ICategoryPathAdjuster {
    @Autowired
    private TranslatorCategoryPathByNameAdjusterService translatorCategoryPathByNameAdjusterService;
    @Autowired
    private ReplacePunctuationMarksCategoryPathAdjusterService replacePunctuationMarksCategoryPathAdjusterService;
    @Autowired
    private SpaceToHyphenCategoryPathAdjusterService spaceToHyphenCategoryPathAdjusterService;
    @Autowired
    private LowerCaseCategoryPathAdjusterService lowerCaseCategoryPathAdjusterService;


    @Override
    public void adjustPaths(List<Category> categories) throws Exception {
        chainAdjustPaths(categories,
                translatorCategoryPathByNameAdjusterService,
                replacePunctuationMarksCategoryPathAdjusterService,
                spaceToHyphenCategoryPathAdjusterService,
                lowerCaseCategoryPathAdjusterService);
    }
}
