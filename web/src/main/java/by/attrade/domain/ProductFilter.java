package by.attrade.domain;

import org.hibernate.annotations.ColumnDefault;
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
@Entity
@Table(name = "product_filter")
public class ProductFilter implements Serializable {

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
    @ColumnDefault("''")
    @Length(max = 40)
    private String data;
}

