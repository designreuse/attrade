package by.attrade.service;

import com.ibm.icu.text.Transliterator;
import org.junit.Assert;
import org.junit.Test;

public class ProductPathCyrillicToLatinExtractorServiceTest {
    @Test
    public void getPath() throws Exception {
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        String result = toLatinTrans.transliterate("привет");
        Assert.assertTrue("privet".equalsIgnoreCase(result));
    }
}