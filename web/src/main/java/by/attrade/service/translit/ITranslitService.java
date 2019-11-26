package by.attrade.service.translit;

import java.util.Map;

public interface ITranslitService {
    default String toTranslit(Map<String, String> letters, String text){
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i<text.length(); i++) {
            String l = text.substring(i, i+1);
            if (letters.containsKey(l)) {
                sb.append(letters.get(l));
            }
            else {
                sb.append(l);
            }
        }
        return sb.toString();
    }
    String toTranslit(String text);
}
