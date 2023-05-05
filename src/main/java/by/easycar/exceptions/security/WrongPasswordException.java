package by.easycar.exceptions.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongPasswordException extends AuthenticationException {

    public WrongPasswordException(String msg) {
        super(msg);
    }
}