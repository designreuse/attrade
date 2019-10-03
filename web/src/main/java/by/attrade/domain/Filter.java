package by.attrade.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Filter implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FILTER_ID")
    private Long id;

    @Column(length = 40)
    @Length(max = 40)
    @NotBlank
    private String name;

    @Column(length = 255)
    @Length(max = 255)
    @NotBlank
    private String description;

    private boolean necessary;

    @ManyToMany(mappedBy = "filters")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    public Set<ProductFilter> productFilters = new HashSet<>();
}
