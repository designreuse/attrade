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
    private boolean extractByFileLoading;
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

    private String countParent;
    private String countChild;
    private String countDivContains;
    private String countDivContains1;
    private String countClass;
    private Integer countIndex;
    private String countValueClass;
    private Integer countValueIndex;

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

    private String countInPackParent;
    private String countInPackChild;
    private String countInPackDivContains;
    private String countInPackDivContains1;
    private String countInPackClass;
    private Integer countInPackIndex;
    private String countInPackValueClass;
    private Integer countInPackValueIndex;

    private String dimensionPackParent;
    private String dimensionPackChild;
    private String dimensionPackDivContains;
    private String dimensionPackDivContains1;
    private String dimensionPackValueClass;
    private Integer dimensionPackValueIndex;

    private String weightPackParent;
    private String weightPackChild;
    private String weightPackDivContains;
    private String weightPackDivContains1;
    private String weightPackValueClass;
    private Integer weightPackValueIndex;

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

    private String descriptionGetElementsByClass;
    private String descriptionImageSelectForWrap;
    private String descriptionImageSelectForRemove;
    private String descriptionImageWrap;
    private String descriptionImageSelectForUnwrap;
    private String descriptionImageSelectContainsForRemove;
    private String descriptionImageSelectNotContains;
    private String descriptionImageSelect;
    private String descriptionImageUrl;

    private String imageParent;
    private String imageSelect;
    private String imageUrl;
}
