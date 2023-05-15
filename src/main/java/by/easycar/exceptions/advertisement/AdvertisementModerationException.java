package by.easycar.exceptions.advertisement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AdvertisementModerationException extends RuntimeException {

    public AdvertisementModerationException(String s) {
        super(s);
    }
}
