package by.easycar.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordRequest {

    @NotBlank(message = "Password must be not empty.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "Password must has at least 1 capital letter and 1 figure and length between 8 and 20. ")
    private String password;
}