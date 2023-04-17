package by.easycar.controllers;

import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
import by.easycar.service.UserService;
import by.easycar.service.verifications.VerificationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    private ResponseEntity<UserPrivate> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/get-contacts/{id}")
    private ResponseEntity<UserPublic> getContacts(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserPublic(id), HttpStatus.OK);
    }

    @PostMapping("")
    private ResponseEntity<String> postUserHandler(@RequestBody UserPrivate user) {
        userService.saveNewUser(user);
        return new ResponseEntity<>("User was created.", HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<String> putUserHandler(@RequestBody UserPrivate user) {
        userService.updateUser(user);
        return new ResponseEntity<>("User was changed.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUserHandler(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User was deleted.", HttpStatus.OK);
    }
}
