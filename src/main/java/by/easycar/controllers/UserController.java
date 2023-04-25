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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    private ResponseEntity<String> registerNewUser(@RequestBody UserRequest newUser) {
        userService.saveNewUser(newUser);
        return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    private ResponseEntity<String> updateUser(
            @RequestBody UserRequest userRequest,
            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        userService.updateUser(userRequest, user.getId());
        return new ResponseEntity<>("User was updated.", HttpStatus.OK);
    }

    @PutMapping("/update-email")
    private ResponseEntity<String> updateEmail(
            @RequestParam String email,
            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
//        userService.updateUser(email, user);
        return new ResponseEntity<>("User was updated.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserInner> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (!user.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getUserInner(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUserHandler(
            @PathVariable Long id,
            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (!user.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>("User was deleted.", HttpStatus.NO_CONTENT);
    }
}
