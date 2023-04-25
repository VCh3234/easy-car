package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UpdateAdvertisementException extends RuntimeException {
    public UpdateAdvertisementException(String message) {
        super(message);
    }
}
