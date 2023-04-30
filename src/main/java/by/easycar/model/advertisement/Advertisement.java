package by.easycar.model.advertisement;

import by.easycar.model.user.UserForAd;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_sequence")
    @SequenceGenerator(catalog = "sequences", name = "ad_sequence", sequenceName = "advertisements_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "ad_id")
    private Long id;
    @Column(name = "ad_creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;
    @Column(name = "ad_up_time", nullable = false)
    private LocalDateTime upTime = LocalDateTime.now();
    @Column(name = "ad_moderated", nullable = false)
    private boolean moderated = false;
    @Column(name = "ad_count_views", nullable = false)
    private Integer viewsCount = 0;
    @Column(name = "ad_price", nullable = false)
    private Integer price;
    @Column(name = "ad_vin", nullable = false)
    private String VINNumber;
    @Column(name = "ad_description", length = 1200, nullable = false)
    private String description;
    @Column(name = "ad_region", nullable = false)
    private String region;
    @Column(name = "ad_mileage", nullable = false)
    private Integer mileage;
    @Column(name = "ad_engine_capacity", nullable = false)
    private Integer engineCapacity;
    @Column(name = "ad_engine_type", nullable = false)
    private String engineType;
    @Column(name = "ad_transmission_type", nullable = false)
    private String transmissionType;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "v_id", nullable = false)
    private Vehicle vehicle;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "u_id", nullable = false)
    private UserForAd user;
    @Embedded
    private ImageData imageData;
}
