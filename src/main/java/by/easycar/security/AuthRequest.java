package by.easycar.security;

import lombok.Data;

@Data
public class AuthRequest {
    private final String email;
    private final String password;
}
