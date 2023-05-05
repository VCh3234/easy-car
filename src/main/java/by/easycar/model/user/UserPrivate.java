package by.easycar.model.user;

import by.easycar.model.Payment;
import by.easycar.model.Role;
import by.easycar.model.advertisement.Advertisement;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @SequenceGenerator(catalog = "sequences", name = "u_sequence", sequenceName = "u_sequence_id")
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;

    @Column(name = "u_update_time", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "u_name", nullable = false)
    private String name;

    @Column(name = "u_phone", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "u_email", unique = true, nullable = false)
    private String email;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_email_verify", nullable = false)
    private boolean verifiedByEmail = false;

    @Column(name = "u_phone_verify", nullable = false)
    private boolean verifiedByPhone = false;

    @Column(name = "u_ups", nullable = false)
    private Integer ups = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Advertisement> advertisements = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();
}