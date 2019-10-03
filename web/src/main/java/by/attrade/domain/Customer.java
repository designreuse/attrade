package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User implements Serializable {
    public static final long serialVersionUID = 1L;
    @Embedded
    private Address address;

    @Column(length = 40)
    @ColumnDefault("''")
    @Length(max = 40)
    private String contactName;

    @Column(length = 40)
    @ColumnDefault("''")
    @Length(max = 40)
    private String contactTitle;

    @Column(length = 12)
    @Pattern(regexp = "375[0-9]{2}[0-9]{7}")
    private String contactPhone;

    @UpdateTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime lastModifiedLDT;
}
