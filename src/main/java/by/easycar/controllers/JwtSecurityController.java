package by.easycar.controllers;

import by.easycar.model.security.AuthRequest;
import by.easycar.service.security.JwtAuthenticationService;
import by.easycar.service.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class JwtSecurityController {
    private final JwtAuthenticationService jwtAuthenticationService;
    private final SecurityService securityService;

    @Autowired
    public JwtSecurityController(JwtAuthenticationService jwtAuthenticationService, SecurityService securityService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    private ResponseEntity<String> getJWTToken(@RequestBody AuthRequest authRequest) {
        if(securityService.authenticateUser(authRequest.getEmail(), authRequest.getPassword())) {
            String token = jwtAuthenticationService.getTokenByEmail(authRequest.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    private void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
