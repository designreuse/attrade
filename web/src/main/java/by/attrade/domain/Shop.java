package by.attrade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(value = "books")
public class Shop implements Serializable {
    public static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_book", joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private Set<Book> books = new HashSet<>();
}
