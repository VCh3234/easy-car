package by.easycar.controllers;

import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.AdvertisementRequest;
import by.easycar.model.requests.SearchParams;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.AdminService;
import by.easycar.service.AdvertisementService;
import by.easycar.service.search.SearchAdvertisementService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/ads")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    private final SearchAdvertisementService searchAdvertisementService;

    private final AdminService adminService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService, SearchAdvertisementService searchAdvertisementService, AdminService adminService) {
        this.advertisementService = advertisementService;
        this.searchAdvertisementService = searchAdvertisementService;
        this.adminService = adminService;
    }

    @GetMapping
    private ResponseEntity<Object> getPublicAdvertisement(@RequestParam(required = false) Long id) {
        if (id == null) {
            List<Advertisement> allAdvertisements = advertisementService.getAllModeratedAdvertisementOrdered();
            return new ResponseEntity<>(allAdvertisements, HttpStatus.OK);
        } else {
            Advertisement advertisement = advertisementService.getPublicById(id);
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        }
    }

    @GetMapping
    private ResponseEntity<List<Moderation>> getModerationsOfUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal userPrincipal) {
        List<Moderation> moderations = adminService.getModerationsOfUser(userPrincipal);
        return new ResponseEntity<>(moderations, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Advertisement>> getAdvertisementsWithFilter(@RequestBody List<SearchParams> searchParams) {
        List<Advertisement> advertisements = searchAdvertisementService.getAllByParams(searchParams);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @GetMapping("/my-ads")
    private ResponseEntity<Object> getInnerAdvertisements(@RequestParam(required = false) Long id,
                                                          @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal userPrincipal) {
        if (id == null) {
            List<Advertisement> advertisements = advertisementService.getAllOfUser(userPrincipal.getId());
            return new ResponseEntity<>(advertisements, HttpStatus.OK);
        } else {
            Advertisement advertisement = advertisementService.getInnerAdvertisementById(id, userPrincipal.getId());
            return new ResponseEntity<>(advertisement, HttpStatus.OK);
        }
    }

    @PostMapping
    private ResponseEntity<String> postNewAdvertisement(@RequestBody @Valid AdvertisementRequest advertisementRequest,
                                                        @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.saveNewAd(advertisementRequest, user.getId());
        return new ResponseEntity<>("Wait moderation.", HttpStatus.OK);
    }

    @PutMapping("/{adId}")
    private ResponseEntity<String> updateAdvertisement(@PathVariable("adId") Long adId,
                                                       @RequestBody @Valid AdvertisementRequest advertisementRequest,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.update(adId, advertisementRequest, user);
        return new ResponseEntity<>("Advertisement was updated.", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{adId}")
    private ResponseEntity<String> deleteAdvertisement(@PathVariable Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.delete(adId, user);
        return new ResponseEntity<>("Was deleted.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/up/{adId}")
    private ResponseEntity<String> upAdvertisement(@PathVariable Long adId,
                                                   @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.upAdvertisement(adId, user.getId());
        return new ResponseEntity<>("Advertisement was upped.", HttpStatus.OK);
    }
}