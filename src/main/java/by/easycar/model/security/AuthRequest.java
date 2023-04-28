package by.easycar.model.security;

import lombok.Data;

@Data
public class AuthRequest {
    private final String email;
    private final String password;
}
