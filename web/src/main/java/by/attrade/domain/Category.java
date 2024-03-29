package by.attrade.domain;

import by.attrade.interceptor.CategoryIndexingInterceptor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@ToString(of = {"name", "parent"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Indexed(interceptor = CategoryIndexingInterceptor.class)
@JsonIgnoreProperties(value = {"products", "properties"})
public class Category implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    @JsonView(Views.Id.class)
    private Long id;

    @Min(0)
    private Long parent;

    @Column(length = 100)
    @Length(max = 100)
    @NotBlank
    @NonNull
    @Fields({
            @Field(index= Index.YES, analyze= Analyze.YES, store = Store.NO, analyzer = @Analyzer(definition = "ngram")),
            @Field(name = "name_ascii", analyze = Analyze.YES, normalizer = @Normalizer(definition = "ascii"), store = Store.NO)
    })
    @JsonView(Views.Name.class)

    private String name;

    @Column(length = 255)
    @Length(max = 255)
    @JsonView(Views.Path.class)
    private String path;

    @Column(length = 150)
    @Length(max = 150)
    private String picture;

    private Integer priority;
    private boolean invisible;

    @JsonView(Views.ProductCount.class)
    @Formula("(select count(*) from Product p where p.category_id = id and p.invisible = 0)")
    private Long productCount;

    @OneToMany(mappedBy = "category")
    @IndexedEmbedded(includePaths = { "name", "code"})
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Property> properties = new ArrayList<>();

    public Category(@Length(max = 30) @NotBlank String name) {
        this.name = name;
    }
}
