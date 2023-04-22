package by.easycar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CreationUserException extends RuntimeException{
    public CreationUserException(String message) {
        super(message);
    }
}
