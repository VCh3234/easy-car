package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FindAdvertisementException extends RuntimeException {

    public FindAdvertisementException(String message) {
        super(message);
    }
}