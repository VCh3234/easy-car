package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FullImageDataException extends RuntimeException {
    public FullImageDataException(String message) {
        super(message);
    }
}