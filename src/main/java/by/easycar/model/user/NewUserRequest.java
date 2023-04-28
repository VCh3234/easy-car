package by.easycar.model.user;

import lombok.Data;

@Data
public class NewUserRequest {
    private UserRequest userRequest;
    private String password;
}
