package by.attrade.service.validation;

import by.attrade.config.validation.RegistrationPasswordConfig;
import by.attrade.service.exception.UserPasswordValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordValidationService {
    @Autowired
    private RegistrationPasswordConfig config;

    public void validate(String password) throws UserPasswordValidationException {
        if (!password.matches(config.getRegex())) {
            throw new UserPasswordValidationException(config.getMessage());
        }
    }
}
