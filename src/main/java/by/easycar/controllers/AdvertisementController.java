package by.easycar.controllers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    private ResponseEntity<List<Advertisement>> getAllAdvertisement() {
        List<Advertisement> allAdvertisements = advertisementService.getAllAdvertisement();
        return new ResponseEntity<>(allAdvertisements, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<Advertisement> addNewAdvertisement(@RequestBody Advertisement advertisement) {
        advertisementService.save(advertisement);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<Advertisement> updateAdvertisement(@RequestBody Advertisement advertisement) {
        advertisementService.update(advertisement);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> updateAdvertisement(@RequestParam Long id) {
        advertisementService.delete(id);
        return new ResponseEntity<>("Was deleted.", HttpStatus.OK);
    }

}
