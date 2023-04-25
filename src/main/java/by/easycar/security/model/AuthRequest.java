package by.easycar.security.model;

import lombok.Data;

@Data
public class AuthRequest {
    private final String email;
    private final String password;
}
