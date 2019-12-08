package by.attrade.util;

public class StringUtil {
    public static String trimIfNotNull(String text) {
        if (text != null) {
            text = text.trim();
        }
        return text;
    }
    public static String renameToNullIfContains(String text, String matcher) {
        if (text.contains(matcher)) {
            return null;
        } else {
            return text;
        }
    }
}
