package by.attrade.repos.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @Override
    public String convertToDatabaseColumn(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
