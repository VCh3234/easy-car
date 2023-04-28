package by.easycar.exceptions.security;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
