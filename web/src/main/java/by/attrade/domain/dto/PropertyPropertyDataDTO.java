package by.attrade.domain.dto;

import by.attrade.domain.Property;
import by.attrade.domain.PropertyData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyPropertyDataDTO {
    private Property property;
    private PropertyData propertyData;
}
