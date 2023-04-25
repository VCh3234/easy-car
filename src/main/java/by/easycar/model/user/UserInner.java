package by.easycar.model.user;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString
public class UserInner {
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
}
