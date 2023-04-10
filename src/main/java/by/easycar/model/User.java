package by.easycar.model;

import by.easycar.model.security.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class User {

    private final static Role ROLE = Role.ROLE_USER;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_sequence")
    @SequenceGenerator(catalog = "sequences", name = "u_sequence", sequenceName = "u_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_phone")
    private String phoneNumber;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_status", nullable = false)
    private String status;

    @ElementCollection
    @CollectionTable(
            name = "images",
            joinColumns = @JoinColumn(name = "'IMAGE_ID'"))
    private Set<Image> images = new HashSet<>();
}
