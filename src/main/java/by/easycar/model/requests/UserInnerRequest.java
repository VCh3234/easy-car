package by.easycar.model.requests;

import by.easycar.model.user.UserPrivate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
