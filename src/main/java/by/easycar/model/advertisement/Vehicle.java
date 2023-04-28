package by.easycar.model.advertisement;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "v_sequence")
    @SequenceGenerator(catalog = "sequences", name = "v_sequence", sequenceName = "v_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "v_id")
    private Integer id;
    @Column(name = "v_brand", nullable = false)
    private String brand;
    @Column(name = "v_model", nullable = false)
    private String model;
    @Column(name = "v_generation", nullable = false)
    private String generation;
    @Column(name = "v_start_production", nullable = false)
    private LocalDate startProduction;
    @Column(name = "v_end_production", nullable = false)
    private LocalDate endProduction;
    @Column(name = "v_body_type", nullable = false)
    private String bodyType;
}
