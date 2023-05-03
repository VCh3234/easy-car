package by.easycar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VerifyMethodNotSupportedException extends RuntimeException {

    public VerifyMethodNotSupportedException(String message) {
        super(message);
    }
}
