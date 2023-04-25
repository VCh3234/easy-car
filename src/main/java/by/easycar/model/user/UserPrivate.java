package by.easycar.model.user;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.payments.Payment;
import by.easycar.model.security.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"payments", "advertisements"})
@Setter
@Getter
@ToString(exclude = {"payments", "advertisements"})

@Entity
@Table(name = "users")
public class UserPrivate {

    public final static Role ROLE = Role.USER;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_sequence")
    @SequenceGenerator(catalog = "sequences", name = "u_sequence", sequenceName = "u_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;

    @Column(name = "u_update_time", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @Column(name = "u_name")
    private String name;

    @Column(name = "u_phone", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "u_email", unique = true, nullable = false)
    private String email;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "u_email_verify")
    private boolean verifiedByEmail = false;
    @Column(name = "u_phone_verify")
    private boolean verifiedByPhone = false;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Payment> payments = new HashSet<>();
    @Column(name = "u_ups")
    private Integer ups = 0;

    public enum UserStatus {
        ACTIVE, BANNED,
    }
}
