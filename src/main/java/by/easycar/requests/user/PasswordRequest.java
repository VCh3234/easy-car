package by.easycar.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordRequest {

    @NotBlank(message = "Password must be not empty.")
    @Length(min = 8, max = 20, message = "Length of the password must be between 8 and 20.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must has at least 1 capital letter and 1 figure. ")
    private String password;
}