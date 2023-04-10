package by.easycar.model;

import by.easycar.model.security.Role;
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

    private final static Role ROLE = Role.ROLE_ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_sequence")
    @SequenceGenerator(catalog = "sequences", name = "adm_sequence", sequenceName = "admins_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "adm_id")
    private Long id;
    @Column(name = "adm_name")
    private String name;

    @Column(name = "adm_status")
    private String status;
}
