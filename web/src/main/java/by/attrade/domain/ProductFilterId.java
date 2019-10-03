package by.attrade.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductFilterId implements Serializable {

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "FILTER_ID")
    private Long filterId;
}
