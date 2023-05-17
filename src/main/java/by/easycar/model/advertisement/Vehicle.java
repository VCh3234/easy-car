package by.easycar.model.advertisement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "v_sequence")
    @SequenceGenerator(name = "v_sequence", sequenceName = "vehicles_v_id_seq", allocationSize = 1)
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