package by.easycar.controllers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.AdvertisementRequest;
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
import java.util.Set;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/public")
    private ResponseEntity<Object> getPublicAdvertisement(@RequestParam Long id) {
        if (id == null) {
            List<Advertisement> allAdvertisements = advertisementService.getAllModeratedAdvertisement();
            return new ResponseEntity<>(allAdvertisements, HttpStatus.OK);
        } else {
            Advertisement advertisement = advertisementService.getPublicById(id);
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        }
    }

    @GetMapping("/of-user")
    private ResponseEntity<Object> getPrivateAdvertisement(@RequestParam("id") Long id, @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity userSecurity) {
        if (id == null) {
            Set<Advertisement> advertisements = advertisementService.getAllOfUser(userSecurity.getId());
            return ResponseEntity.ok(advertisements);
        } else {
            Advertisement advertisement = advertisementService.getById(id);
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    private ResponseEntity<String> addNewAdvertisement(@RequestBody AdvertisementRequest advertisementRequest,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        if (advertisementService.saveNewAd(advertisementRequest, user.getId())) {
            return new ResponseEntity<>("Wait moderation.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something go wrong.", HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{adId}")
    private ResponseEntity<String> updateAdvertisement(@PathVariable("adId") Long adId,
                                                       @RequestBody AdvertisementRequest advertisementRequest,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.update(adId, advertisementRequest, user);
        return new ResponseEntity<>("Advertisement was updated.", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> updateAdvertisement(@RequestParam Long adId, @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.delete(adId, user);
        return new ResponseEntity<>("Was deleted.", HttpStatus.OK);
    }
}
