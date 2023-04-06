package by.easycar.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString

@Entity
@Immutable
@Table(name = "moderations")
public class Moderation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mo_sequence")
    @SequenceGenerator(catalog = "sequences", name = "mo_sequence", sequenceName = "moderation_sequence_id", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(name = "mo_message")
    private String message;
    @Transient
    private Admin admin;
}
