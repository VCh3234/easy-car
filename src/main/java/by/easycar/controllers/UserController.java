package by.easycar.controllers;

import by.easycar.model.requests.NewUserRequest;
import by.easycar.model.requests.UserInnerRequest;
import by.easycar.model.requests.UserRequest;
import by.easycar.model.user.UserSecurity;
import by.easycar.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    private ResponseEntity<String> registerNewUser(@RequestBody NewUserRequest newUserRequest) {
        if (userService.saveNewUser(newUserRequest.getUserRequest(), newUserRequest.getPassword())) {
            return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something go wrong.", HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    private ResponseEntity<String> updateUser(@RequestBody UserRequest userRequest, @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (userService.updateUser(userRequest, user.getId())) {
            return new ResponseEntity<>("User was updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something go wrong.",HttpStatus.CONFLICT);
    }

    @PutMapping("/update-password")
    private ResponseEntity<String> updatePasswordUser(@RequestParam String password,
                                                      @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (userService.updatePassword(password, user.getId())) {
            return new ResponseEntity<>("Password was updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something go wrong.",HttpStatus.CONFLICT);
    }

    @GetMapping("")
    private ResponseEntity<UserInnerRequest> getUserInnerById(@AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        return new ResponseEntity<>(userService.getUserInner(user.getId()), HttpStatus.OK);
    }

    @DeleteMapping("")
    private ResponseEntity<String> deleteUser(@AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (userService.deleteUserById(user.getId())) {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("User was deleted.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
