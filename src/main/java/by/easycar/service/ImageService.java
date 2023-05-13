package by.easycar.service;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.ImageData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private static Path ROOT_PATH = Paths.get("images");

    private AdvertisementService advertisementService;

    @Autowired
    public ImageService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
        if (!Files.exists(ROOT_PATH)) {
            try {
                Files.createDirectory(ROOT_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveImage(MultipartFile file, Long adId, UUID uuid) throws IOException {
        Path pathWithoutUuid = ROOT_PATH.resolve(String.valueOf(adId));
        checkDirectory(pathWithoutUuid);
        Path fullPath = pathWithoutUuid.resolve(uuid.toString() + ".jpg");
        Files.copy(file.getInputStream(), fullPath);
    }

    private void deleteImage(Long adId, String uuid) throws IOException {
        Files.delete(ROOT_PATH.resolve(String.valueOf(adId)).resolve(uuid + ".jpg"));
    }

    private void checkDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    @Transactional
    public void postNewImages(MultipartFile file, Long adId, Long userId) throws IOException {
        Advertisement advertisement = advertisementService.getInnerAdvertisementById(adId, userId);
        if (!advertisement.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Your id don't match with id in advertisement.");
        }
        ImageData imageData = advertisement.getImageData();
        if (imageData == null) {
            imageData = new ImageData();
        }
        UUID uuid = UUID.randomUUID();
        imageData.postNewImage(uuid);
        advertisement.setImageData(imageData);
        this.saveImage(file, adId, uuid);
        advertisement.setModerated(false);
        advertisementService.saveChanges(advertisement);
    }

    @Transactional
    public void replaceImage(MultipartFile file, String oldUuid, Long adId, Long userId) throws IOException {
        Advertisement advertisement = advertisementService.getInnerAdvertisementById(adId, userId);
        if (!advertisement.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Your id don't match with id in advertisement.");
        }
        ImageData imageData = advertisement.getImageData();
        if (imageData == null) {
            imageData = new ImageData();
            advertisement.setImageData(imageData);
        }
        UUID newUuid = UUID.randomUUID();
        imageData.replace(oldUuid, newUuid);
        this.deleteImage(adId, oldUuid);
        this.saveImage(file, adId, newUuid);
        advertisement.setModerated(false);
        advertisementService.saveChanges(advertisement);
    }

    @Transactional
    public void deleteImage(String oldImage, Long adId, Long userId) throws IOException {
        Advertisement advertisement = advertisementService.getInnerAdvertisementById(adId, userId);
        if (!advertisement.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Your id don't match with id in advertisement.");
        }
        this.deleteImage(adId, oldImage);
        advertisement.getImageData().replace(oldImage, null);
        advertisementService.saveChanges(advertisement);
    }

    public byte[] getImage(Long adId, String uuid) throws IOException {
        Path path = ROOT_PATH.resolve(String.valueOf(adId)).resolve(uuid + ".jpg");
        return Files.readAllBytes(path);
    }

    public static void deleteDir(Long adId) {
        Path path = ROOT_PATH.resolve(String.valueOf(adId));
        deleteFiles(path.toFile());
    }

    private static void deleteFiles(File dirOrFile) {
        File[] allContents = dirOrFile.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFiles(file);
            }
        }
        dirOrFile.delete();
    }

    public void deleteImageForAdmin(Long adId, String imageUuid) throws IOException {
        this.deleteImage(adId, imageUuid);
    }
}