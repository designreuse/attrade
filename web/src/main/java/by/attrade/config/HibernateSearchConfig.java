package by.attrade.config;

import by.attrade.domain.dto.PictureMediaDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "hibernate.search")
public class HibernateSearchConfig {
    private boolean init;
    private boolean purgeAllOnStart;
    private boolean optimizeOnFinish;
    private Integer batchSizeToLoadObjects;
    private Integer threadsToLoadObjects;
    private String regexAnyChar;
    private Integer minGramSize;
}
