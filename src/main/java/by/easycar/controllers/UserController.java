package by.easycar.controllers;

import by.easycar.model.user.UserPrincipal;
import by.easycar.requests.user.PasswordRequest;
import by.easycar.requests.user.UserInnerResponse;
import by.easycar.requests.user.UserRegisterRequest;
import by.easycar.requests.user.UserRequest;
import by.easycar.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.saveNewUser(userRegisterRequest);
        return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<String> updateUser(@RequestBody @Valid UserRequest userRequest,
                                              @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.updateUser(userRequest, user.getId());
        return new ResponseEntity<>("User was updated.", HttpStatus.OK);
    }

    @PutMapping("/update-password")
    private ResponseEntity<String> updatePasswordUser(@RequestBody @Valid PasswordRequest passwordRequest,
                                                      @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.updatePassword(passwordRequest.getPassword(), user.getId());
        return new ResponseEntity<>("Password was updated.", HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<UserInnerResponse> getUserInner(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        return new ResponseEntity<>(userService.getUserInner(user.getId()), HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<String> deleteUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>("User was deleted.", HttpStatus.NO_CONTENT);
    }
}