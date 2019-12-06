package by.attrade.service.categoryPathExtractor;

import by.attrade.domain.Category;
import by.attrade.service.translit.CyrillicLatinTranslitService;
import by.attrade.service.translit.SpaceHyphenTranslitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlOfNameCategoryPathExtractorService implements ICategoryPathExtractor {
    @Autowired
    private CyrillicLatinTranslitService cyrillicLatinTranslitService;
    @Autowired
    private SpaceHyphenTranslitService spaceHyphenTranslitService;


    @Override
    public String getPath(Category category) throws Exception {
        String s = cyrillicLatinTranslitService.toTranslit(category.getName());
        s = spaceHyphenTranslitService.toTranslit(s);
        return s.toLowerCase();
    }
}
