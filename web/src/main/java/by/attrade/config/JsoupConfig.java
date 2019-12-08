package by.attrade.config;

import by.attrade.loader.YamlPropertyLoaderFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:jsoup.yml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "jsoup")
public class JsoupConfig {
    private String userAgent;
    private String referrer;
    private int timeout;
}
