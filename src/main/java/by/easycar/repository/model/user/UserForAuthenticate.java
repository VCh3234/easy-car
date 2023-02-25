package by.easycar.repository.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserForAuthenticate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String pass;

    @Column(name = "role_id")
    @Enumerated
    private Role role;
}
