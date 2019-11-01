package by.attrade.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
    public static boolean isValidUrl(String url) {
        try{
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
}
