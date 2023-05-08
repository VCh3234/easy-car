package by.easycar.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Name must be not empty.")
    private final String name;

    @NotBlank(message = "Password must be not empty.")
    private final String password;
}