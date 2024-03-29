package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

/*
https://stackoverflow.com/questions/23837561/jpa-2-0-many-to-many-with-extra-column
 */
@ToString(of = {"product", "property"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_property")
public class ProductProperty implements Serializable {
    public static final long serialVersionUID = 1L;
    @EmbeddedId
    private ProductPropertyId id = new ProductPropertyId();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "property_id")
    private Property property;

    @Embedded
    private PropertyData propertyData;

    private Integer priority;

    private boolean invisible;
    private boolean supplement; // TODO - delete (from DB too)
}

