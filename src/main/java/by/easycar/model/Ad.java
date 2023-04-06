package by.easycar.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "advertises")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_sequence")
    @SequenceGenerator(catalog = "sequences", name = "ad_sequence", sequenceName = "ad_sequence_id", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(name = "ad_creating_date", nullable = false, updatable = false)
    private LocalDate creatingDate;
    @Column(name = "ad_up_time", nullable = false)
    private LocalDateTime upTime;
    @Column(name = "ad_count_views", nullable = false)
    private Integer viewsCount;
    @Column(name = "ad_price", nullable = false)
    private Integer price;
    @Column(name = "ad_vin")
    private String VINNumber;
    @Column(name = "ad_description")
    private String description;
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
