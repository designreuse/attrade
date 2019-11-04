package by.attrade.service.validation;

import by.attrade.service.exception.UserPasswordValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordValidationService {
    @Value("${registration.validation.password.regex}")
    private String regex;
    @Value("${registration.validation.password.message}")
    private String message;

    public void validate(String password) throws UserPasswordValidationException {
        if (!password.matches(regex)) {
            throw new UserPasswordValidationException(message);
        }
    }
}
