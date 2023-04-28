package by.easycar.model.administration;

import by.easycar.model.Role;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "admins")
public class Admin {

    public final static Role ROLE = Role.ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_sequence")
    @SequenceGenerator(catalog = "sequences", name = "adm_sequence", sequenceName = "admins_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "adm_id")
    private Long id;
    @Column(name = "adm_name")
    private String name;
    @Column(name = "adm_password")
    private String password;
}
