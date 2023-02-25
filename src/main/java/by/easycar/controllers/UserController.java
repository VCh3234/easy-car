package by.easycar.controllers;

import by.easycar.repository.model.user.User;
import by.easycar.service.UserService;
import by.easycar.service.verifications.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final VerificationService verificationServiceImpl;
    private final UserService userService;

    @GetMapping("/{id}")
    private User getByIdHandler(@PathVariable long id) {
        return userService.getById(id);
    }

    @GetMapping("")
    private List<User> getAllUsersHandler() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    private ResponseEntity<String> postUserHandler(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PutMapping("")
    private ResponseEntity<String> putUserHandler(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUserHandler(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    @PostMapping("/verify")
    private ResponseEntity<String> verifyUser(@RequestParam long id) {
        if(!verificationServiceImpl.verify(id)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
