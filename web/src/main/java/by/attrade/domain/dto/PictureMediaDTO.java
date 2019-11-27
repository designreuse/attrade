package by.attrade.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class PictureMediaDTO {
    private String path;
    private String media;
    private Double compressionPercent;
}
