package by.easycar.controllers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.security.UserSecurity;
import by.easycar.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    private ResponseEntity<List<Advertisement>> getAllModeratedAdvertisement() {
        List<Advertisement> allAdvertisements = advertisementService.getAllModeratedAdvertisement();
        return new ResponseEntity<>(allAdvertisements, HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<String> addNewAdvertisement(@RequestBody Advertisement advertisement,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (advertisementService.saveNewAd(advertisement, user)) {
            return new ResponseEntity<>("Wait moderation", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something go wrong", HttpStatus.CONFLICT);
    }

    @PutMapping("/update")
    private ResponseEntity<Advertisement> updateAdvertisement(@RequestBody Advertisement advertisement,
                                                              @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.update(advertisement,user);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> updateAdvertisement(@RequestParam Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.delete(adId, user);
        return new ResponseEntity<>("Was deleted.", HttpStatus.OK);
    }
}
