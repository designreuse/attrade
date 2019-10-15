package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(of = {"user"})
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class VerificationToken implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @Column(length = 16)
    @NotNull
    @Length(max = 16)
    private String token = UUID.randomUUID().toString().substring(0,16);

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime expiryDate;

    public boolean isExpiredToken(){
        return expiryDate.isBefore(LocalDateTime.now());
    }
}