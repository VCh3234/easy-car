package by.easycar.controllers;

import by.easycar.model.security.UserSecurity;
import by.easycar.service.verifications.VerificationResolver;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerifyController {

    private final VerificationResolver verificationResolver;

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
