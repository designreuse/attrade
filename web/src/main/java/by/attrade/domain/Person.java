package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "customer_id")
public class Person extends Customer implements Serializable {
    public static final long serialVersionUID = 1L;
    @Column(length = 40)
    @Length(min = 2, max = 40)
    private String firstName;

    @Column(length = 40)
    @Length(min = 2, max = 40)
    private String lastName;

    @Column(length = 40)
    @Length(min = 2, max = 40)
    private String fatherName;

    @UpdateTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime lastModifiedLDT;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime createdLDT;

    public String getLastNameWithInitials() {
        return lastName + " " + firstName.charAt(0) + "." + fatherName.charAt(0);
    }

    public String getInitialsWithLastName() {
        return firstName.charAt(0) + "." + fatherName.charAt(0) + " " + lastName;
    }
}

