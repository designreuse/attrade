package by.attrade.config.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:validation.properties")
@ConfigurationProperties(prefix = "registration.password")
public class UserPasswordConfig {
    private String regex;
    private String message;
}
