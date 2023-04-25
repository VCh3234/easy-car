package by.easycar.model.payments;

import by.easycar.model.user.UserPrivate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString

@Entity
@Table(name = "payments")
@Immutable
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_sequence")
    @SequenceGenerator(catalog = "sequences", name = "p_sequence", sequenceName = "p_sequence_id", initialValue = 1, allocationSize = 1)
    @Column(name = "p_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "p_user_id")
    private UserPrivate user;
    @Column(name = "p_date_time")
    @CreationTimestamp
    private LocalDateTime payTime;
    @Column(name = "p_bank_name")
    private String bankName;
    @Column(name = "p_operation_name")
    private String operationName;
    @Column(name = "p_transaction_number")
    private String transactionNumber;
}
