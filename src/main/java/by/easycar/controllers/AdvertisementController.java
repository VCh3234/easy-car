package by.easycar.controllers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.AdvertisementRequest;
import by.easycar.model.user.UserSecurity;
import by.easycar.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ad")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/public")
    private ResponseEntity<Object> getPublicAdvertisement(@RequestParam(required = false) Long id) {
        if (id == null) {
            List<Advertisement> allAdvertisements = advertisementService.getAllModeratedAdvertisementOrdered();
            return new ResponseEntity<>(allAdvertisements, HttpStatus.OK);
        } else {
            Advertisement advertisement = advertisementService.getPublicById(id);
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        }
    }

    @GetMapping("/of-user")
    private ResponseEntity<Object> getPrivateAdvertisement(@RequestParam(required = false) Long id,
                                                           @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity userSecurity) {
        if (id == null) {
            Set<Advertisement> advertisements = advertisementService.getAllOfUser(userSecurity.getId());
            return ResponseEntity.ok(advertisements);
        } else {
            Advertisement advertisement = advertisementService.getInnerAdvertisementById(id);
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

    @DeleteMapping("/delete/{adId}")
    private ResponseEntity<String> updateAdvertisement(@PathVariable Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.delete(adId, user);
        return new ResponseEntity<>("Was deleted.", HttpStatus.OK);
    }

    @PutMapping("/{adId}")
    private ResponseEntity<String> upAdvertisement(@PathVariable Long adId,
                                                   @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        advertisementService.upAdvertisement(adId, user.getId());
        return new ResponseEntity<>("Advertisement was upped.", HttpStatus.OK);
    }
}
