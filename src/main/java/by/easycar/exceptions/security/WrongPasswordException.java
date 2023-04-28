package by.easycar.exceptions.security;

import org.springframework.security.core.AuthenticationException;

public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
