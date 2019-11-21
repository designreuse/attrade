package by.attrade.service;

import by.attrade.config.MessageSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourceOnlyLanguageService implements MessageSource{
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageSourceConfig messageSourceConfig;

    @Override
    public String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {
        if (messageSourceConfig.isOnlyLanguage()){
            locale = new Locale(locale.getLanguage());
        }
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException {
        if (messageSourceConfig.isOnlyLanguage()){
            locale = new Locale(locale.getLanguage());
        }
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        if (messageSourceConfig.isOnlyLanguage()){
            locale = new Locale(locale.getLanguage());
        }
        return messageSource.getMessage(resolvable, locale);
    }
}
