package by.easycar.controllers;

import by.easycar.model.requests.AuthRequest;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import by.easycar.service.security.admin.AdminSecurityService;
import by.easycar.service.security.user.UserJwtAuthenticationService;
import by.easycar.service.security.user.UserSecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtSecurityController {

    private final UserSecurityService userSecurityService;

    private final UserJwtAuthenticationService userJwtAuthenticationService;

    private final AdminSecurityService adminSecurityService;

    private final AdminJwtAuthenticationService adminJwtAuthenticationService;

    @Autowired
    public JwtSecurityController(UserJwtAuthenticationService userJwtAuthenticationService, AdminJwtAuthenticationService adminJwtAuthenticationService, AdminSecurityService adminSecurityService, UserSecurityService userSecurityService) {
        this.userJwtAuthenticationService = userJwtAuthenticationService;
        this.adminJwtAuthenticationService = adminJwtAuthenticationService;
        this.adminSecurityService = adminSecurityService;
        this.userSecurityService = userSecurityService;
    }

    @PostMapping("/login")
    private ResponseEntity<String> getJwtTokenForUser(@RequestBody @Valid AuthRequest authRequest) {
        if (userSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())) {
            String token = userJwtAuthenticationService.getToken(authRequest.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/admin/login")
    private ResponseEntity<String> getJwtTokenForAdmin(@RequestBody @Valid AuthRequest authRequest) {
        if (adminSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())) {
            String token = adminJwtAuthenticationService.getToken(authRequest.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}