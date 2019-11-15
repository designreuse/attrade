package by.attrade.service.validation;

import by.attrade.config.validation.RegistrationEmailConfig;
import by.attrade.service.exception.UserEmailValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEmailValidationService {
    @Autowired
    private RegistrationEmailConfig config;

    public void validate(String password) throws UserEmailValidationException {
        if (!password.matches(config.getRegex())) {
            throw new UserEmailValidationException(config.getMessage());
        }
    }
}
