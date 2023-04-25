package by.easycar.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserAuthenticateException extends AuthenticationException {
    public UserAuthenticateException(String message) {
        super(message);
    }
}
