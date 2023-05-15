package by.easycar.controller;

import by.easycar.model.dto.AuthRequest;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import by.easycar.service.security.admin.AdminSecurityService;
import by.easycar.service.security.user.UserJwtAuthenticationService;
import by.easycar.service.security.user.UserSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "JWT auth")
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

    @Operation(summary = "Get JWT token for user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = {@ExampleObject(name = "User Vlad", value = "{\n" +
                    "    \"name\":\"uladcherap99@gmail.com\",\n" +
                    "    \"password\":\"1Qqqqqqq\"\n" +
                    "}")
            })))
    @PostMapping("/login")
    private ResponseEntity<String> getJwtTokenForUser(@RequestBody @Valid AuthRequest authRequest) {
        if (userSecurityService.authenticateUser(authRequest.getName(), authRequest.getPassword())) {
            String token = userJwtAuthenticationService.getToken(authRequest.getName());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Get JWT token for admin",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = {@ExampleObject(name = "Admin admin", value = "{\n" +
                    "    \"name\":\"SUPER_ADMIN\",\n" +
                    "    \"password\":\"ROOT\"\n" +
                    "}")
            })))
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