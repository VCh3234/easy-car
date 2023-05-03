package by.easycar.controllers;

import by.easycar.model.user.UserSecurity;
import by.easycar.service.verifications.VerificationResolver;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerifyController {

    private final VerificationResolver verificationResolver;

    @Autowired
    public VerifyController(VerificationResolver verificationResolver) {
        this.verificationResolver = verificationResolver;
    }

    @PutMapping("/{code}")
    private ResponseEntity<String> verifyUser(@PathVariable String code,
                                              @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (verificationResolver.verify(user.getId(), code)) {
            return new ResponseEntity<>("User was verified.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t verify user.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{verifyType}")
    private ResponseEntity<String> sendCode(@PathVariable String verifyType,
                                            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (verificationResolver.sendMessage(user.getId(), verifyType)) {
            return new ResponseEntity<>("Message was send.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can`t verify user.", HttpStatus.BAD_REQUEST);
    }
}
