package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CantFindOldUuidException extends RuntimeException {
    public CantFindOldUuidException(String message) {
        super(message);
    }
}
