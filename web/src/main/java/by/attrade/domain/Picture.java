package by.attrade.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Picture {
    public Picture(@NotEmpty String path, @NotNull Integer priority) {
        this.path = path;
        this.priority = priority;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 50)
    @NotEmpty
    private String path;

    @NotNull
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "product_picture_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_icon_id")
    private Product productIcon;
}
