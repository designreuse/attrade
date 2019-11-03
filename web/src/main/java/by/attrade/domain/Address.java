package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address implements Serializable{
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
