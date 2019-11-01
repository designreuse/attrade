package by.attrade.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
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
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_filter")
public class ProductProperty implements Serializable {
    @EmbeddedId
    private ProductFilterId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("filterId")
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @Column(length = 40)
    @Length(max = 40)
    private String data;

    private double doubleData;
    private int integerData;
    private boolean booleanData;

    private boolean visible;
    private boolean supplement;
}

