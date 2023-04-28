package by.easycar.model.administration;

import by.easycar.model.advertisement.Advertisement;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "adm_id", nullable = false)
    private Admin admin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_id", nullable = false)
    private Advertisement advertisement;
}
