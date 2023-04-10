package by.easycar.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString

@Entity
@Immutable
@Table(name = "moderations")
@Access(AccessType.FIELD) //Default - added for demonstration
public class Moderation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mo_sequence")
    @SequenceGenerator(catalog = "sequences", name = "mo_sequence", sequenceName = "moderations_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "mo_id")
    private Long id;

    @Column(name = "mo_creation_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Column(name = "mo_message", nullable = false)
    private String message;
    @Transient
    private Admin admin;

    public Moderation(String message, Admin admin) {
        this.message = message;
        this.admin = admin;
    }
}
