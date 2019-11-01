package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255)
    @Length(max = 255)
    private String url;

    @Column(length = 255)
    @Length(max = 255)
    @NotBlank
    private String name;

    @Column(length = 60)
    @Length(max = 60)
    @NotBlank
    private String code;

    @Column(length = 1000)
    @Length(max = 1000)
    private String description;

    @Column(length = 20)
    @Length(max = 20)
    private String vendor;

    private int quantityInStock;
    @Basic(fetch = FetchType.LAZY)
    private int quantityReserved;
    @Basic(fetch = FetchType.LAZY)
    private int quantityFuture;

    private double price;

    @Embedded
    private Dimension dimension;

    private double weight; // gram

    @Column(length = 20)
    @Length(max = 20)
    @Basic(fetch = FetchType.LAZY)
    private String deliveryCountry;

    @Column(length = 20)
    @Length(max = 20)
    private String madeCountry;

    @OneToOne
    private Picture icon;

    @OneToOne
    private Picture picture;

    @OneToMany(mappedBy = "productPicture")
    List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "productIcon")
    List<Picture> icons = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ProductDetail> productDetails = new HashSet<>();

    @OneToMany(mappedBy = "property")
    private Set<ProductProperty> productProperties = new HashSet<>();
}
