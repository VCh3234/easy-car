package by.easycar.model.user;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class UserPrivate {

    private final static Role ROLE = Role.ROLE_USER;

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

    @Column(name = "u_phone")
    private String phoneNumber;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "u_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "u_is_moderated", nullable = false)
    private Boolean isChecked;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements = new HashSet<>();

    public enum UserStatus {
        UNVERIFIED,
        VERIFIED,
        BANNED,
    }
}
