package by.attrade.service.productPathExtractor;

import by.attrade.domain.Product;
import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Service;

@Service
public class CyrillicToLatinOfNameProductPathExtractorService implements IProductPathExtractor{
    @Override
    public String getPath(Product product) {
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        return toLatinTrans.transliterate(product.getName());
    }
}
