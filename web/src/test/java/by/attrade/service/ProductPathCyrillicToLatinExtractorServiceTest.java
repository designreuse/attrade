package by.attrade.service;

import com.ibm.icu.text.Transliterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Enumeration;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ProductPathCyrillicToLatinExtractorServiceTest {
    @Test
    public void getPath() throws Exception {
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        String result = toLatinTrans.transliterate("привет");
        Assert.assertTrue("privet".equalsIgnoreCase(result));
    }
}