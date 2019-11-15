package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Config {
    private List<String> social;
    private List<String> idAttr;
    private List<String> nameAttr;
}
