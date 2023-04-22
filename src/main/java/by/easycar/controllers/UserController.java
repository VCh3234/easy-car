package by.easycar.controllers;

import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
import by.easycar.security.UserSecurity;
import by.easycar.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    private ResponseEntity<String> registerNewUser(@RequestBody UserPrivate user) {
        userService.saveNewUser(user);
        return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    private ResponseEntity<String> updateUser(
            @RequestBody UserPrivate userPrivate,
            @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user
            ) {
        if(user.getId().equals(userPrivate.getId())) {
            return new ResponseEntity<>("Access denied.", HttpStatus.FORBIDDEN);
        }
        userService.updateUser(userPrivate);
        return new ResponseEntity<>("User was updated.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserInner> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal @Parameter(hidden = true) User user
    ) {
        return new ResponseEntity<>(userService.getUserInner(id), HttpStatus.OK);
    }

    @GetMapping("/get-contacts/{id}")
    private ResponseEntity<UserPublic> getContacts(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserPublic(id), HttpStatus.OK);
    }





    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUserHandler(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User was deleted.", HttpStatus.OK);
    }
}
