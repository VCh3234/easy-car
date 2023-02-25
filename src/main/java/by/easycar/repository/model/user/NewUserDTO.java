package by.easycar.repository.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class NewUserDTO { //todo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String pass;

    @Column(name = "phone_number")
    private String phoneNumber;
}
