package by.easycar.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "status")
    private Status status;

    @Column(name = "role")
    private String role;

    enum Status {
        UNVERIFIED,
        VERIFIED,
        DELETED,
        BANNED
    }

}
