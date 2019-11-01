package by.attrade.domain.test;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Entity1 implements Serializable{
    public static final long serialVersionUID = 1L;
    @EmbeddedId
    private PkAsPkAndFk id;

    @ManyToOne
    @MapsId("entity2Id")
    private Entity2 entity2;

    @Column(length = 100)
    @Length(max = 100)
    @NotBlank
    private String name;

    @Column(length = 255)
    @Length(max = 255)
    private String description;

    private boolean visible;
    private boolean supplement;

    public Entity1(PkAsPkAndFk id) {
        this.id = id;
    }
}
