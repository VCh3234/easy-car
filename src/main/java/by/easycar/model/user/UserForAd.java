package by.easycar.model.user;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class UserForAd {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_sequence")
    @SequenceGenerator(catalog = "sequences", name = "u_sequence", sequenceName = "u_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_phone", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "u_email", unique = true, nullable = false)
    private String email;
}
