package by.easycar.controllers;

import by.easycar.model.requests.AuthRequest;
import by.easycar.service.security.JwtAuthenticationService;
import by.easycar.service.security.SecurityService;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import by.easycar.service.security.admin.AdminSecurityService;
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
    private final AdminJwtAuthenticationService adminJwtAuthenticationService;
    private final AdminSecurityService adminSecurityService;
    private final SecurityService securityService;

    @Autowired
    public JwtSecurityController(JwtAuthenticationService jwtAuthenticationService, AdminJwtAuthenticationService adminJwtAuthenticationService, AdminSecurityService adminSecurityService, SecurityService securityService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.adminJwtAuthenticationService = adminJwtAuthenticationService;
        this.adminSecurityService = adminSecurityService;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    private ResponseEntity<String> getJWTToken(@RequestBody AuthRequest authRequest) {
        if(securityService.authenticateUser(authRequest.getEmail(), authRequest.getPassword())) {
            String token = jwtAuthenticationService.getToken(authRequest.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/admin/login")
    private ResponseEntity<String> getJWTTokenForAdmin(@RequestBody AuthRequest authRequest) {
        if(adminSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())) {
            String token = adminJwtAuthenticationService.getToken(authRequest.getName());
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
