package by.easycar.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "advertisements")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_sequence")
    @SequenceGenerator(catalog = "sequences", name = "ad_sequence", sequenceName = "advertisements_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "ad_id")
    private Long id;
    @Column(name = "ad_creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;
    @Column(name = "ad_up_time", nullable = false)
    private LocalDateTime upTime;
    @Column(name = "ad_count_views", nullable = false)
    private Integer viewsCount;
    @Column(name = "ad_price", nullable = false)
    private Integer price;
    @Column(name = "ad_vin")
    private String VINNumber;
    @Column(name = "ad_description", length = 1200)
    private String description;
    @Formula("substring(ad_description FROM 1 for 100)") //TODO: Test this formula
    private String shortDescription;
    @Column(name = "ad_region")
    private String region;
    @Column(name = "ad_phone", nullable = false)
    private String phone;
    @Column(name = "ad_mileage")
    private Integer mileage;
    @Column(name = "ad_status", nullable = false)
    private String status;
    @Transient
    private Vehicle vehicle;
    @Transient
    private User user;
    @Transient
    private Set<File> images;
}
