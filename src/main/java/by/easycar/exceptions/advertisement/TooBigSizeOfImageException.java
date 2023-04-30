package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TooBigSizeOfImageException extends RuntimeException {
    public TooBigSizeOfImageException(String message) {
        super(message);
    }
}
