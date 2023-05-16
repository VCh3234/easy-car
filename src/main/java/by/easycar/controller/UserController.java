package by.easycar.controller;

import by.easycar.model.dto.user.PasswordRequest;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.dto.user.UserRegisterRequest;
import by.easycar.model.dto.user.UserRequest;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register new user")
    @PostMapping
    private ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.saveNewUser(userRegisterRequest);
        return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
    }

    @Operation(summary = "Update user", security = {@SecurityRequirement(name = "User JWT")})
    @PutMapping
    private ResponseEntity<String> updateUser(@RequestBody @Valid UserRequest userRequest,
                                              @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.updateUser(userRequest, user.getId());
        return new ResponseEntity<>("User was updated.", HttpStatus.OK);
    }

    @Operation(summary = "Update password", security = {@SecurityRequirement(name = "User JWT")})
    @PutMapping("/update-password")
    private ResponseEntity<String> updatePasswordUser(@RequestBody @Valid PasswordRequest passwordRequest,
                                                      @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.updatePassword(passwordRequest.getPassword(), user.getId());
        return new ResponseEntity<>("Password was updated.", HttpStatus.OK);
    }

    @Operation(summary = "Get inner user", security = {@SecurityRequirement(name = "User JWT")})
    @GetMapping
    private ResponseEntity<UserInnerResponse> getUserInner(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        return new ResponseEntity<>(userService.getUserInner(user.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "User JWT")})
    @DeleteMapping
    private ResponseEntity<String> deleteUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>("User was deleted.", HttpStatus.NO_CONTENT);
    }
}