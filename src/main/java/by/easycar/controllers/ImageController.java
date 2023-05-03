package by.easycar.controllers;

import by.easycar.model.user.UserSecurity;
import by.easycar.service.ImageService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
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

    @DeleteMapping("/delete/{adId}")
    public ResponseEntity<String> deleteImage(@RequestParam String oldImage,
                                              @PathVariable Long adId,
                                              @AuthenticationPrincipal @Parameter(hidden = true) UserSecurity user) {
        try {
            imageService.deleteImage(oldImage, adId, user.getId());
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{adId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String adId, @RequestParam String uuid) {
        try {
            byte[] image = imageService.getImage(adId, uuid);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg ");
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
