package by.attrade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(length = 100)
    @Length(max = 100)
    @NotBlank
    private String name;

    @Column(length = 60)
    @Length(max = 60)
    @NotBlank
    private String code;

    @Column(length = 1000)
    @Length(max = 1000)
    @NotBlank
    private String description;

    @Column(length = 20)
    @Length(max = 20)
    @NotBlank
    private String vendor;

    private int quantityInStock;
    private int quantityReserved;
    private int quantityFuture;

    private double price;

    @Column(length = 20)
    @ColumnDefault("''")
    @Length(max = 20)
    private String deliveryCountry;

    @Column(length = 20)
    @ColumnDefault("''")
    @Length(max = 20)
    private String madeCountry;

    @Column(length = 512)
    @Lob
    private byte[] icon;

    @Column(length = 2048)
    @Lob
    private byte[] image;

    @OneToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductDetail> productDetails = new HashSet<>();

    @OneToMany(mappedBy = "filter")
    private Set<ProductFilter> productFilters = new HashSet<>();
}
