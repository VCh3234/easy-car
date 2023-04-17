package by.easycar.controllers;

import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UsersMapper;
import by.easycar.repository.ModerationRepository;
import by.easycar.repository.UserRepository;
import by.easycar.service.ModerationService;
import by.easycar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mod")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModerationController {
    private final UserService userService;
    private final ModerationService moderationService;

    @GetMapping("/get-unchecked-users")
    private Set<UserInner> getUncheckedUsers() {
        Set<UserInner> userInnerSet = userService.getAllByIsChecked();
        return userInnerSet;
    }

    @PutMapping("/accept")
    private ResponseEntity<String> acceptUserWithId(@RequestParam Long id) {
        userService.acceptUserWithId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reject")
    private ResponseEntity<String> rejectUserWithId(@RequestParam(name = "user_id") Long userId,
                                                    @RequestParam(name = "admin_id") Long adminId,
                                                    @RequestParam(name = "moderation_message") String message) {
        moderationService.saveModeration(userId, adminId, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
