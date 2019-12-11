package by.attrade.util;

import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlEncodingTest {
    @org.junit.Test
    public void test() throws MalformedURLException {
        String urlString = " \t \n https://texenergo.ru \t \n";
        String actual = urlString.trim();
        Assert.assertFalse(actual.contains(" "));
        Assert.assertFalse(actual.contains("\t"));
        Assert.assertFalse(actual.contains("\b"));
        Assert.assertFalse(actual.contains("\n"));
        URL url = new URL(urlString);
        Assert.assertFalse(url.toString().contains(" "));
        Assert.assertFalse(StringUtil.removeSpaces(urlString).contains(" "));
    }


}
