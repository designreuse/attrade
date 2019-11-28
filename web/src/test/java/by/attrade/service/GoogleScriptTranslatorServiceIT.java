package by.attrade.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleScriptTranslatorServiceIT {
    @Autowired
    private GoogleScriptTranslatorService service;
    @Test
    public void translate() throws Exception {
        String text = "Hello world!";
        String expected = "Привет, мир!";
        String actual = service.translate("en", "ru", text);
        Assert.assertTrue(expected.equals(actual));
    }
    @Test
    public void translateDifficult() throws Exception {
        String text = "Электромагнитный пускатель";
        String expected = "Electromagnetic starter";
        String actual = service.translate("ru", "en", text);
        Assert.assertTrue(expected.equals(actual));
    }

}