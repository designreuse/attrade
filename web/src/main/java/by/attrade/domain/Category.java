package by.attrade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Category implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(0)
    private Long parent;

    @Column(length = 30)
    @Length(max = 30)
    @NotBlank
    private String name;

    @Column(length = 512)
    @Lob
    private byte[] icon;

    @OneToMany(mappedBy = "category")
    Set<Product> products = new HashSet<>();
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "category_filter",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "filter_id")})
    private Set<Filter> filters = new HashSet<>();
}
