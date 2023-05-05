package by.easycar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WrongOperationNameException extends RuntimeException {

    public WrongOperationNameException(String message) {
        super(message);
    }
}