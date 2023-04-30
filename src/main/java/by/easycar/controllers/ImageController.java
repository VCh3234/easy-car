package by.easycar.controllers;

import by.easycar.model.security.UserSecurity;
import by.easycar.service.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{adId}")
    public ResponseEntity<String> uploadNew(@RequestParam MultipartFile file,
                                         @PathVariable Long adId,
                                         @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        try {
            imageService.postNewImages(file, adId, user.getId());
            return ResponseEntity.ok("Image was upload");
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/upload/{adId}")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file,
                                         @RequestParam String oldImage,
                                         @PathVariable Long adId,
                                         @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        try {
            imageService.postNewImagesWithReplace(file, oldImage, adId, user.getId());
            return ResponseEntity.ok("Image was changed.");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage() + e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
