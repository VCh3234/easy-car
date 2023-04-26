package by.easycar.controllers;

import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserRequest;
import by.easycar.security.model.UserSecurity;
import by.easycar.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    private ResponseEntity<String> registerNewUser(@RequestBody UserRequest newUser) {
        if (userService.saveNewUser(newUser)) {
            return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    private ResponseEntity<String> updateUser(@RequestBody UserRequest userRequest, @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (userService.updateUser(userRequest, user.getId())) {
            return new ResponseEntity<>("User was updated.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("")
    private ResponseEntity<UserInner> getUserInnerById(@AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
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
