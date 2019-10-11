package by.attrade.domain;

import by.attrade.converter.LocalDateToTimestampConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Builder
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Employee extends Person implements Serializable {
    public static final long serialVersionUID = 1L;
    @Length(max = 20)
    @NotBlank
    private String title;

    @CreationTimestamp
    @Convert(converter = LocalDateToTimestampConverter.class)
    private LocalDate hireLD;

    @NotNull
    @Convert(converter = LocalDateToTimestampConverter.class)
    private LocalDate expireContractLD;

    @CreationTimestamp
    @Convert(converter = LocalDateToTimestampConverter.class)
    private LocalDate birthLD;

    @Lob
    private byte[] photo;

    @Min(0)
    private int salary;

    @Singular
    @OneToMany(mappedBy = "employee", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new HashSet<>();
}
