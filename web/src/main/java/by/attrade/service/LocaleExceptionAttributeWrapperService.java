package by.attrade.service;

import by.attrade.config.MessageSourceConfig;
import by.attrade.util.LocaleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

@Service
public class LocaleExceptionAttributeWrapperService {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageSourceConfig config;

    public void wrapAsAttributes(List<LocaleException> errors, Model model, Locale locale) {
        for (LocaleException e : errors) {
            if (config.isOnlyLanguage()){
                locale = new Locale(locale.getLanguage());
            }
            String message = messageSource.getMessage(e.getCode(), e.getArgs(), locale);
            model.addAttribute(e.getAttrName(), message);
        }
    }
}
