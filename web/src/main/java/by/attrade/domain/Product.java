package by.attrade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @Basic(fetch = FetchType.LAZY)
    private int quantityReserved;
    @Basic(fetch = FetchType.LAZY)
    private int quantityFuture;

    private double price;

    @Column(length = 20)
    @ColumnDefault("''")
    @Length(max = 20)
    @Basic(fetch = FetchType.LAZY)
    private String deliveryCountry;

    @Column(length = 20)
    @ColumnDefault("''")
    @Length(max = 20)
    private String madeCountry;

    @Column(length = 255)
    private String icon;

    @Column(length = 255)
    private String image;

    @OneToMany(mappedBy = "productGallery")
    Set<Picture> gallery = new HashSet<>();

    @OneToMany(mappedBy = "productHistoryGallery")
    Set<Picture> historyGallery = new HashSet<>();

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
