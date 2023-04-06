package by.easycar.controllers;

import by.easycar.model.NewUserDTO;
import by.easycar.model.User;
import by.easycar.service.UserService;
import by.easycar.service.verifications.VerificationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final VerificationResolver verificationResolver;
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
        if (user.getId() != null) {
            return new ResponseEntity<>("Request must be without 'id' field.", HttpStatusCode.valueOf(400));
        }
        userService.saveUser(user);
        return new ResponseEntity<>("User was created.", HttpStatusCode.valueOf(201));
    }

    @PutMapping("")
    private ResponseEntity<String> putUserHandler(@RequestBody NewUserDTO newUser) {
        userService.saveUser(newUser);
        return new ResponseEntity<>("User was changed.", HttpStatusCode.valueOf(202));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUserHandler(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User was deleted.", HttpStatusCode.valueOf(202));
    }

    @PutMapping("/verify")
    private ResponseEntity<String> verifyUser(@RequestParam long id, @RequestParam("verificationType") String verificationType) {
        if (verificationResolver.verify(id, verificationType)) {
            return new ResponseEntity<>("User was verified.", HttpStatusCode.valueOf(202));
        }
        return new ResponseEntity<>("Can`t verify user.", HttpStatusCode.valueOf(400));
    }
}
