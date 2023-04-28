package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongUserException extends RuntimeException {
    public WrongUserException(String message) {
        super(message);
    }
}
