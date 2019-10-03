package by.attrade.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Data
@Embeddable
public class Address {
    @Column(length = 100)
    @Length(max = 100)
    private String city;
    @Column(length = 20)
    @Length(max = 20)
    private String street;
    @Column(length = 20)
    @Length(max = 20)
    private String apartment;
    @Column(length = 6)
    @Length(max = 6)
    @Pattern(regexp = "^\\d{6}", message = "{by.attrade.domain.Address.zipcode.pattern.error}")
    private String zipCode;
}
