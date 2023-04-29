package by.easycar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Deprecated
@ResponseStatus(HttpStatus.OK)
public class ForVerifyStubException extends RuntimeException {
    public ForVerifyStubException(String message) {
        super(message);
    }
}
