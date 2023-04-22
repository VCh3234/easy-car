package by.easycar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UpdatingUserException extends RuntimeException{
    public UpdatingUserException(String message) {
        super(message);
    }
}
