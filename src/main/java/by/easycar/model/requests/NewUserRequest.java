package by.easycar.model.requests;

import lombok.Data;

@Data
public class NewUserRequest {

    private UserRequest userRequest;

    private String password;
}
