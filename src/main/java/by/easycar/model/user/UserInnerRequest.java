package by.easycar.model.user;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.payments.Payment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString
public class UserInnerRequest {
    private Long id;
    private LocalDate creationDate;
    private LocalDateTime updateTime;
    private String name;
    private String phoneNumber;
    private String email;
    private UserPrivate.UserStatus status;
    private Integer ups;
    private boolean isVerifiedByEmail;
    private boolean isVerifiedByPhone;
    private Set<Advertisement> advertisements = new HashSet<>();
    private Set<Payment> payments = new HashSet<>();
}
