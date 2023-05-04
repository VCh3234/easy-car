package by.easycar.requests.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRequest {

    @NotNull(message = "Name must be not null.")
    @NotBlank(message = "Name must be not empty.")
    @Length(min = 2, max = 30, message = "Length of the name must be between 2 and 30")
    private String name;

    @NotNull(message = "Phone number must be not null.")
    @NotBlank(message = "Phone number must be not empty.")
    @Length(min = 9, max = 9, message = "Length of the number must be 9.")
    private String phoneNumber;

    @NotNull(message = "Email must be not null.")
    @NotBlank(message = "Email must be not empty.")
    @Email(message = "Wrong email format.")
    private String email;
}
