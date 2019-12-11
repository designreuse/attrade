package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class PropertyData {
    @Column(length = 500)
    @Length(max = 500)
    private String data;

    private Double doubleData;
    private Integer integerData;
    private boolean booleanData;
    public PropertyData(String data){
        this.data = data;
    }
}
