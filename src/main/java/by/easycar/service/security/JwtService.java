package by.easycar.service.security;

import org.springframework.security.core.Authentication;

public interface JwtService {

     String getToken(String email);

     boolean isValidToken(String token);

     Authentication getAuthentication(String token);
}
