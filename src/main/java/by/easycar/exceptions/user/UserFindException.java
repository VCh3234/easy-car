package by.easycar.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserFindException extends RuntimeException {
    public UserFindException(String message) {
        super(message);
    }
}
