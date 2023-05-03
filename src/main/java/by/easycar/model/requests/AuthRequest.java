package by.easycar.model.requests;

import lombok.Data;

@Data
public class AuthRequest {

    private final String email;

    private final String name;

    private final String password;
}
