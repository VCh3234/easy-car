package by.easycar.requests.user;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @Valid
    private UserRequest userRequest;

    @Valid
    private PasswordRequest passwordRequest;
}
