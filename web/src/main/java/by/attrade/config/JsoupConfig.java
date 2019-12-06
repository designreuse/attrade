package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jsoup")
public class JsoupConfig {
    private String userAgent;
    private String referrer;
    private int timeout;
}
