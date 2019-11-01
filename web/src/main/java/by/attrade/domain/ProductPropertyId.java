package by.attrade.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductFilterId implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "filter_id")
    private Long filterId;
}
