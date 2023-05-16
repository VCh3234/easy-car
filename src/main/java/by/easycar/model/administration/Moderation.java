package by.easycar.model.administration;

import by.easycar.model.advertisement.Advertisement;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = {"advertisement", "admin"})
@EqualsAndHashCode(exclude = {"advertisement", "admin"})
@Entity
@Immutable
@Table(name = "moderation")
@Access(AccessType.FIELD)
public class Moderation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mo_sequence")
    @SequenceGenerator(catalog = "sequences", name = "mo_sequence", sequenceName = "moderation_mo_id_seq", allocationSize = 1)
    @Column(name = "mo_id")
    private Long id;

    @Column(name = "mo_creation_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "mo_message", nullable = false)
    @Length(min = 8, max = 500)
    @NotBlank
    private String message;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "adm_id", nullable = false)
    private Admin admin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_id")
    private Advertisement advertisement;
}