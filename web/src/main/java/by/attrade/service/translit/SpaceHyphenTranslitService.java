package by.attrade.service.translit;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SpaceHyphenTranslitService implements ITranslitService{
    private static final Map<String, String> letters = new HashMap<String, String>();
    static {
        letters.put(" ", "-");
    }
    @Override
    public String toTranslit(String text) {
        return toTranslit(letters, text);
    }
}
