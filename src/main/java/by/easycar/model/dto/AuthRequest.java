package by.easycar.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Name must be not empty.")
    private String name;

    @NotBlank(message = "Password must be not empty.")
    private String password;
}