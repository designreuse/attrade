package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import by.attrade.converter.LocalDateToTimestampConverter;
import by.attrade.interceptor.ProductIndexingInterceptor;
import by.attrade.type.Unit;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Normalizer;
import org.hibernate.search.annotations.NormalizerDef;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Length;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString(of = {"id", "name", "code"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@NormalizerDef(
        name = "ascii",
        filters = {
                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class), // To handle diacritics such as "é"
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        }
)
@AnalyzerDef(name = "ngram",
        tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//                @TokenFilterDef(factory = UpperCaseFilterFactory.class),
//                @TokenFilterDef(factory = SnowballPorterFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = NGramFilterFactory.class,
                        params = {
                                @Parameter(name = "minGramSize", value = "3"),
                                @Parameter(name = "maxGramSize", value = "5")
                        })
        }
)
@Indexed(interceptor = ProductIndexingInterceptor.class)
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    @JsonView(Views.Id.class)
    private Long id;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime creationLDT;

    @Column(length = 300)
    @Length(max = 300)
    @NotBlank
    @Fields({
            @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, analyzer = @Analyzer(definition = "ngram")),
            @Field(name = "name_ascii", analyze = Analyze.YES, normalizer = @Normalizer(definition = "ascii"), store = Store.NO)
    })
    @JsonView(Views.Name.class)
    private String name;

    @Column(length = 500)
    @Length(max = 500)
    @JsonView(Views.Path.class)
    private String path;

    @Column(length = 500)
    @Length(max = 500)
    private String url;

    @Column(length = 60, unique = true)
    @Length(max = 60)
    @NotBlank
    @Fields({
            @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, analyzer = @Analyzer(definition = "ngram")),
            @Field(name = "code_ascii", analyze = Analyze.YES, normalizer = @Normalizer(definition = "ascii"), store = Store.NO),
    })

    @JsonView(Views.Code.class)
    private String code;
    private String vendorCode;

    @Column(length = 13, unique = true)
    @Length(max = 13)
    private String barcode;

    @Column(length = 4000)
    @Length(max = 4000)
    private String description;

    @JsonView(Views.QuantityInStock.class)
    private Integer quantityInStock;

    @JsonView(Views.QuantitySupplier.class)
    private Integer quantitySupplier;

    @Basic(fetch = FetchType.LAZY)
    private Integer quantityReserved;

    @Basic(fetch = FetchType.LAZY)
    private Integer quantityFuture;

    @JsonView(Views.Price.class)
    private Double price;

    private Double vat;

    private Double discount;

    @Embedded
    private Dimension dimension;

    @AttributeOverrides(
            {
                    @AttributeOverride(name = "height", column = @Column(name = "HEIGHT_PACK")),
                    @AttributeOverride(name = "width", column = @Column(name = "WIDTH_PACK")),
                    @AttributeOverride(name = "depth", column = @Column(name = "DEPTH_PACK")),
            }
    )
    @Embedded
    private Dimension dimensionPack;

    @AttributeOverrides(
            {
                    @AttributeOverride(name = "height", column = @Column(name = "HEIGHT_CARRY")),
                    @AttributeOverride(name = "width", column = @Column(name = "WIDTH_CARRY")),
                    @AttributeOverride(name = "depth", column = @Column(name = "DEPTH_CARRY")),
            }
    )
    @Embedded
    private Dimension dimensionCarry;

    private Double weight; // kg
    private Double weightPack; // kg
    private Double weightCarry; // kg
    @Column(length = 20)
    @Length(max = 20)
    @Basic(fetch = FetchType.LAZY)
    private String deliveryCountry;

    @Column(length = 20)
    @Length(max = 20)
    private String madeCountry;

    @JsonView(Views.Picture.class)
    @Column(length = 400)
    @Length(max = 400)
    private String picture;


    @Enumerated(EnumType.STRING)
    @JsonView(Views.Unit.class)
    private Unit unit = Unit.ITEM;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.Unit.class)
    private Unit unitPack = Unit.ITEM;

    @Enumerated(EnumType.STRING)
    private Unit unitCarry = Unit.ITEM;

    private Integer count;
    private Integer countInPack;
    private Integer countInPackCarry;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @SortableField
    private boolean popular;

    private boolean fresh;
    private boolean invisible;

    @OneToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @Column(length = 60)
    @Length(max = 60)
    private String vendorShort;

    @Column(length = 255)
    @Length(max=255)
    private String vendorFull;

    @Column(length = 255)
    @Length(max = 255)
    private String vendorIcon;

    @Column(length = 255)
    @Length(max = 255)
    private String serviceCenter;

    @OneToOne
    @IndexedEmbedded(includePaths = {"name"})
    @JsonView(Views.Category.class)
    private Category category;

    @OneToMany(mappedBy = "productPicture")
    List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "productDescriptionPicture")
    List<Picture> descriptionPictures = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductDetail> productDetails = new HashSet<>();

    @OneToMany(mappedBy = "property")
    private Set<ProductProperty> productProperties = new HashSet<>();

    public double getPriceWithDiscount() {
        return price - (price * discount / 100);
    }
}
