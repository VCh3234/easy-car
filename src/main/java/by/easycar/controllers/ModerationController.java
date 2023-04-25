package by.easycar.controllers;

import by.easycar.service.ModerationService;
import by.easycar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mod")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModerationController {
    private final UserService userService;
    private final ModerationService moderationService;


    @PutMapping("/accept")
    private ResponseEntity<String> acceptUserWithId(@RequestParam Long id) {
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
