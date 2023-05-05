package by.easycar.exceptions.advertisement.images;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DeleteImageException extends RuntimeException {
    public DeleteImageException(String message) {
        super(message);
    }
}
