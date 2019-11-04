package by.attrade.domain;

import by.attrade.interceptor.CategoryIndexingInterceptor;
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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString(of = {"name", "parent"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Indexed(interceptor = CategoryIndexingInterceptor.class)
public class Category implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    private Long id;

    @Min(0)
    private Long parent;

    @Column(length = 100)
    @Length(max = 100)
    @NotBlank
    @NonNull
    @Field(index= Index.YES, analyze= Analyze.YES, store = Store.NO, analyzer = @Analyzer(definition = "ngram"))
    private String name;

    @OneToOne
    private Picture icon;

    @OneToOne
    private Picture image;

    private Integer priority;
    private boolean invisible;

    @OneToMany(mappedBy = "category")
    @IndexedEmbedded(includePaths = { "name", "code"})
    private Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "category")
    private Set<Property> properties = new HashSet<>();

    public Category(@Length(max = 30) @NotBlank String name) {
        this.name = name;
    }
}
