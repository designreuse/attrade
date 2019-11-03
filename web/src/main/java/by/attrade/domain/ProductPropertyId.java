package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ProductPropertyId implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "property_id")
    private Long propertyId;
}
