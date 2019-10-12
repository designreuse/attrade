package by.attrade.domain;

import by.attrade.converter.LocalDateTimeToTimestampConverter;
import by.attrade.converter.PasswordConverter;
import by.attrade.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ToString(of = {"id", "username", "password", "email", "active"})
@EqualsAndHashCode(of = {"id"})
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "usr")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails, Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 24)
    @Length(min = 3, max = 24, message = "{by.attrade.domain.User.username.error}")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9_-]{8,}$",
            message = "{by.attrade.domain.User.password.error}")
    @Convert(converter = PasswordConverter.class)
    private String password;

    private boolean active;

    // oAoth2 (google)
    private String sub;

    @UpdateTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    private LocalDateTime lastModifiedLDT;

    @Email(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&" +
            "'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b" +
            "\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:" +
            "[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]" +
            "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*" +
            "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-" +
            "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
            message = "{by.attrade.domain.User.email.error}")
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_subscribtions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscribtions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")})
    private Set<User> subscriptions = new HashSet<>();

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
