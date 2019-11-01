package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "customer_id")
public class Company extends Customer implements Serializable {
    public static final long serialVersionUID = 1L;
    @Column(length = 8)
    @Length(min = 8, max = 8)
    private String taxId;

    @Column(length = 40)
    @Length(max = 40)
    @NotBlank
    private String restrictedName;

    @Column(length = 100)
    @NotBlank
    @Length(max = 100)
    private String fullName;
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "city", column = @Column(name = "F_HOME_CITY", length = 100)),
                    @AttributeOverride(name = "street", column = @Column(name = "F_HOME_STREET", length = 20)),
                    @AttributeOverride(name = "apartment", column = @Column(name = "F_HOME_APARTMENT", length = 20)),
                    @AttributeOverride(name = "zipCode", column = @Column(name = "F_HOME_ZIPCODE", length = 6))
            }
    )
    @Embedded
    private Address homeAddress;
}
