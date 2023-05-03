package by.easycar.model.advertisement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "v_body_type", nullable = false)
    private String bodyType;
}
