package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FindImageDataException extends RuntimeException {
    public FindImageDataException(String message) {
        super(message);
    }
}
