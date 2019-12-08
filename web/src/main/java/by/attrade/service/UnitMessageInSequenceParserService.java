package by.attrade.service;

import by.attrade.type.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UnitMessageInSequenceParserService {
    @Autowired
    private MessageSourceOnlyLanguageService messageSourceOnlyLanguageService;

    public Unit findUnitThroughMessagesSurroundedBraces(String item, Locale locale) {
        Unit unit = null;
        boolean found = false;
        for (Unit u: Unit.values()) {
            if (found) break;
            String code = u.toString().toLowerCase() + "_sequence";
            String unit_sequence = messageSourceOnlyLanguageService.getMessage(code, null, locale);
            String[] split = unit_sequence.split(";");
            for (int i = 0; i < split.length; i++) {
                split[i] = "("+split[i]+")";
            }
            for (String aSplit : split) {
                if (item.contains(aSplit)) {
                    unit = u;
                    found = true;
                    break;
                }
            }
        }
        return unit;
    }

}
