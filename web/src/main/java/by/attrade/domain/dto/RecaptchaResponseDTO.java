package by.attrade.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

@ToString
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaResponseDTO {
    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
