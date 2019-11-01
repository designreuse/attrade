package by.attrade.domain.test;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class PkAsPkAndFk implements Serializable{
    public static final long serialVersionUID = 1L;
    private Long entity1Id;
    private Long entity2Id;
}
