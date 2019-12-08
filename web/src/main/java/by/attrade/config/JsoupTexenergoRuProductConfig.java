package by.attrade.config;

import by.attrade.loader.YamlPropertyLoaderFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Locale;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:jsoup.yml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties(prefix = "jsoup.texenergo.ru.product")
public class JsoupTexenergoRuProductConfig {
    private String url;
    private Locale locale;
    private String defaultPictureFileName;
    private String nameParent;
    private String nameChild;
    private Integer nameIndexElement;
    private String codeParent;
    private String codeChild;
    private Integer codeIndexElement;
    private String vendorCodeParent;
    private String vendorCodeChild;
    private Integer vendorCodeIndexElement;
    private String categoriesParent;
    private String categoriesChild;
    private Integer categoriesStartWithIndex;

    private String propertiesParent;
    private String propertiesChild;
    private String propertiesDivContains;
    private String propertiesClass;
    private String propertiesValueClass;

    private String countInPackParent;
    private String countInPackChild;
    private String countInPackDivContains;
    private String countInPackDivContains1;
    private String countInPackClass;
    private Integer countInPackIndex;
    private String countInPackValueClass;
    private Integer countInPackValueIndex;

    private String dimensionParent;
    private String dimensionChild;
    private String dimensionDivContains;
    private String dimensionDivContains1;
    private String dimensionValueClass;
    private Integer dimensionValueIndex;

    private String weightParent;
    private String weightChild;
    private String weightDivContains;
    private String weightDivContains1;
    private String weightValueClass;
    private Integer weightValueIndex;

    private String carryCountInPackParent;
    private String carryCountInPackChild;
    private String carryCountInPackDivContains;
    private String carryCountInPackDivContains1;
    private String carryCountInPackClass;
    private Integer carryCountInPackIndex;
    private String carryCountInPackValueClass;
    private Integer carryCountInPackValueIndex;

    private String carryDimensionParent;
    private String carryDimensionChild;
    private String carryDimensionDivContains;
    private String carryDimensionDivContains1;
    private String carryDimensionValueClass;
    private Integer carryDimensionValueIndex;

    private String carryWeightParent;
    private String carryWeightChild;
    private String carryWeightDivContains;
    private String carryWeightDivContains1;
    private String carryWeightValueClass;
    private Integer carryWeightValueIndex;

    private String barcodeParent;
    private String barcodeChild;
    private String barcodeDivContains;
    private String barcodeValueClass;
    private Integer barcodeValueIndex;

    private String descriptionClass;

    private String imageParent;
    private String imageChild;
    private String imageAttribute;
}
