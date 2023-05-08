package by.easycar.model.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserInnerResponse {

    private Long id;

    private LocalDate creationDate;

    private LocalDateTime updateTime;

    private String name;

    private String phoneNumber;

    private String email;

    private Integer ups;

    private boolean isVerifiedByEmail;

    private boolean isVerifiedByPhone;
}