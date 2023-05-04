package by.easycar.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull(message = "Name must be not null.")
    @NotBlank(message = "Name must be not empty.")
    private final String name;

    @NotNull(message = "Password must be not null.")
    @NotBlank(message = "Password must be not empty.")
    private final String password;
}