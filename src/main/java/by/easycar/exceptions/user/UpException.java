package by.easycar.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UpException extends RuntimeException {

    public UpException(String message) {
        super(message);
    }
}