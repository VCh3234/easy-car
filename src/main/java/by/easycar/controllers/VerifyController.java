package by.easycar.controllers;

import by.easycar.service.verifications.VerificationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerifyController {

    private final VerificationResolver verificationResolver;

    @PutMapping
    private ResponseEntity<String> verifyUser(@RequestParam long id, @RequestParam("verificationType") String verificationType) {
        if (verificationResolver.verify(id, verificationType)) {
            return new ResponseEntity<>("User was verified.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t verify user.", HttpStatus.BAD_REQUEST);
    }
}
