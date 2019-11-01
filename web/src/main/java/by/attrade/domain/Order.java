package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import by.attrade.type.OrderStatus;
import by.attrade.type.ShipVia;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class Order implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    private OrderDetail orderDetail;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime createLDT = LocalDateTime.now();

    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime requiredLDT;

    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime shippedLDT;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private ShipVia shipVia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freight_id")
    private Freight freight;

    @Embedded
    private Address shipAddress;
}
