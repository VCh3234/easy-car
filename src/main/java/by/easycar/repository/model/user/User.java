package by.easycar.repository.model.user;

import by.easycar.repository.model.user.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_of_creating")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private LocalDate dateOfCreating;

    @Column(name = "user_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "status_id")
    @Enumerated
    private UserStatus status; //TODO

    @Column(name = "role_id")
    @Enumerated
    private Role role; //TODO

    public enum UserStatus {
        UNVERIFIED,
        VERIFIED,
        BANNED,
        DISABLED,
        DELETED
    }
}
