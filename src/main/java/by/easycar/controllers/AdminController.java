package by.easycar.controllers;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.user.UserInnerResponse;
import by.easycar.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    private ResponseEntity<List<Moderation>> getInnerUser() {
        List<Moderation> moderation = adminService.getAllModeration();
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @GetMapping("/get-user/{userId}")
    private ResponseEntity<UserInnerResponse> getInnerUser(@PathVariable Long userId) {
        UserInnerResponse user = adminService.getUserInner(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-advertisement/{adId}")
    private ResponseEntity<Advertisement> getInnerAdvertisement(@PathVariable Long adId) {
        Advertisement advertisement = adminService.getInnerAdvertisement(adId);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @DeleteMapping("/user")
    private ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
        return new ResponseEntity<>("User was deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/advertisement")
    private ResponseEntity<String> deleteAdvertisement(@RequestParam Long adId) {
        adminService.deleteAdvertisement(adId);
        return new ResponseEntity<>("Advertisement was deleted.", HttpStatus.OK);
    }

    @GetMapping("/advertisements-for-moderation")
    private ResponseEntity<List<Advertisement>> getAdvertisementsForModeration() {
        List<Advertisement> advertisements = adminService.getAllAdvertisementsNotModerated();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @GetMapping("/get-payments/{userId}")
    private ResponseEntity<List<Payment>> getAllPaymentsOfUser(@PathVariable Long userId) {
        List<Payment> payments = adminService.getAllPaymentsOfUser(userId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PutMapping("/accept-advertisement")
    private ResponseEntity<String> acceptAdvertisement(@RequestParam Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        adminService.accept(adId, admin);
        return new ResponseEntity<>("Advertisement was accepted.", HttpStatus.OK);
    }

    @PutMapping("/reject-advertisement")
    private ResponseEntity<String> rejectAdvertisement(@RequestParam Long adId,
                                                       @RequestParam String message,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        adminService.rejectAdvertisement(adId, admin, message);
        return new ResponseEntity<>("Advertisement was rejected.", HttpStatus.OK);
    }
}