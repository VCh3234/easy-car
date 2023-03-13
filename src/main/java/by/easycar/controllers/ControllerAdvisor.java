package by.easycar.controllers;

import by.easycar.service.verifications.VerifyMethodNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice //TODO ???
public class ControllerAdvisor {

    @ExceptionHandler(VerifyMethodNotSupportedException.class)
    public ResponseEntity<String> s(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
