package by.easycar.controllers;

import by.easycar.model.user.UserPrincipal;
import by.easycar.service.verifications.VerificationResolver;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{code}")
    private ResponseEntity<String> verifyUser(@PathVariable String code) {
        verificationResolver.verify(code);
        return new ResponseEntity<>("User was verified.", HttpStatus.OK);
    }

    @PostMapping("/{verifyType}")
    private ResponseEntity<String> sendCode(@PathVariable String verifyType,
                                            @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        verificationResolver.sendMessage(user.getId(), verifyType);
        return new ResponseEntity<>("Message was send.", HttpStatus.OK);
    }
}