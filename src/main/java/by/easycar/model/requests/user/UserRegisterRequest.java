package by.easycar.model.requests.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @Valid
    @NotNull(message = "User request must be not null.")
    private UserRequest userRequest;

    @Valid
    @NotNull(message = "Password request must be not null.")
    private PasswordRequest passwordRequest;
}