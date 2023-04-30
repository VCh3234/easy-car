package by.easycar.controllers;

import by.easycar.model.administration.Admin;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.service.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/get-all-advertisements-for-moderation")
    private ResponseEntity<List<Advertisement>> getAdvertisementsForModeration(@AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        List<Advertisement> advertisements = adminService.getAllAdvertisementsNotModerated();
        return ResponseEntity.ok(advertisements);
    }

    @PutMapping("/accept-advertisement")
    private ResponseEntity<String> acceptAdvertisement(@RequestParam Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        if(adminService.accept(adId)) {
            return ResponseEntity.ok("Advertisement was accepted.");
        } else {
            return ResponseEntity.badRequest().body("Something go wrong.");
        }
    }
}
