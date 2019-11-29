package by.attrade.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jsoup.s3.ru.product")
public class JsoupS3ruProductConfig {
    private String url;
    private String nameParent;
    private String nameChild;
    private String codeParent;
    private String codeChild;
    private Integer codeIndexElement;
    private String supplierCodeParent;
    private String supplierCodeChild;
    private Integer supplierCodeIndexElement;
    private String categoriesParent;
    private String categoriesChild;
    private Integer categoriesStartWithIndex;
    private String filtersParent;
    private String filtersChild;
    private String imageParent;
    private String imageChild;
}
