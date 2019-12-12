package by.attrade.service.productPathExtractor;

import by.attrade.domain.Product;
import by.attrade.service.translit.CyrillicLatinTranslitService;
import by.attrade.service.translit.SpaceHyphenTranslitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlOfNameProductPathExtractorService implements IProductPathExtractor {
    @Autowired
    private CyrillicLatinTranslitService cyrillicLatinTranslitService;
    @Autowired
    private SpaceHyphenTranslitService spaceHyphenTranslitService;


    @Override
    public String getPath(Product product) {
        String s = cyrillicLatinTranslitService.toTranslit(product.getName());
        s = spaceHyphenTranslitService.toTranslit(s);
        return s.toLowerCase();
    }
}
