package by.easycar.controller;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.AdvertisementRequest;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.SearchParams;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.AdminService;
import by.easycar.service.AdvertisementService;
import by.easycar.service.search.SearchAdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Advertisement")
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

    @Operation(summary = "Get all public advertisements")
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

    @Operation(summary = "Get moderation of user", security = {@SecurityRequirement(name = "User JWT")})
    @GetMapping("/moderation")
    private ResponseEntity<List<ModerationResponse>> getModerationOfUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal userPrincipal) {
        List<ModerationResponse> moderation = adminService.getModerationOfUser(userPrincipal);
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @Operation(summary = "Get advertisements by search params",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = {@ExampleObject(name = "Search by price and brand", value = "[\n" +
                    "  {\n" +
                    "    \"entity\": \"advertisement\",\n" +
                    "    \"key\": \"price\",\n" +
                    "    \"operation\": \"<\",\n" +
                    "    \"value\": \"7000\"\n" +
                    "  }," +
                    "{\n" +
                    "    \"entity\": \"vehicle\",\n" +
                    "    \"key\": \"brand\",\n" +
                    "    \"operation\": \":\",\n" +
                    "    \"value\": \"BMW\"\n" +
                    "  }\n" +
                    "]")
            })))
    @PostMapping("/search")
    public ResponseEntity<List<Advertisement>> getAdvertisementsWithFilter(@RequestBody List<SearchParams> searchParams) {
        List<Advertisement> advertisements = searchAdvertisementService.getAllByParams(searchParams);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Operation(summary = "Get advertisements of user", security = {@SecurityRequirement(name = "User JWT")})
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

    @Operation(summary = "Create new advertisement", security = {@SecurityRequirement(name = "User JWT")})
    @PostMapping
    private ResponseEntity<String> postNewAdvertisement(@RequestBody @Valid AdvertisementRequest advertisementRequest,
                                                        @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.saveNewAd(advertisementRequest, user.getId());
        return new ResponseEntity<>("Wait moderation.", HttpStatus.OK);
    }

    @Operation(summary = "Update advertisement", security = {@SecurityRequirement(name = "User JWT")})
    @PutMapping("/{adId}")
    private ResponseEntity<String> updateAdvertisement(@PathVariable("adId") Long adId,
                                                       @RequestBody @Valid AdvertisementRequest advertisementRequest,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.update(adId, advertisementRequest, user);
        return new ResponseEntity<>("Advertisement was updated.", HttpStatus.OK);
    }

    @Operation(summary = "Delete advertisement", security = {@SecurityRequirement(name = "User JWT")})
    @DeleteMapping("/{adId}")
    private ResponseEntity<String> deleteAdvertisement(@PathVariable Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.delete(adId, user);
        return new ResponseEntity<>("Advertisement was deleted.", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Up advertisement", security = {@SecurityRequirement(name = "User JWT")})
    @PutMapping("/up/{adId}")
    private ResponseEntity<String> upAdvertisement(@PathVariable Long adId,
                                                   @AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal user) {
        advertisementService.upAdvertisement(adId, user.getId());
        return new ResponseEntity<>("Advertisement was upped.", HttpStatus.OK);
    }
}