package by.easycar.service;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrincipal;
import by.easycar.repository.AdminRepository;
import by.easycar.repository.ModerationRepository;
import by.easycar.service.mappers.ModerationMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AdminService {

    private final AdvertisementService advertisementService;

    private final ModerationRepository moderationRepository;

    private final UserService userService;

    private final PaymentService paymentService;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModerationMapper moderationMapper;

    private final ImageService imageService;

    @Autowired
    public AdminService(AdvertisementService advertisementService, ModerationRepository moderationRepository, UserService userService, PaymentService paymentService, AdminRepository adminRepository, PasswordEncoder passwordEncoder, ModerationMapper moderationMapper, ImageService imageService) {
        this.advertisementService = advertisementService;
        this.moderationRepository = moderationRepository;
        this.userService = userService;
        this.paymentService = paymentService;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.moderationMapper = moderationMapper;
        this.imageService = imageService;
    }

    public List<Advertisement> getAllAdvertisementsNotModerated() {
        return advertisementService.getAllNotModeratedAdvertisement();
    }

    @Transactional
    public void accept(Long adId, Admin admin) {
        advertisementService.acceptModeration(adId);
        Moderation moderation = new Moderation();
        moderation.setMessage("Accept advertisement moderation");
        moderation.setAdmin(admin);
        moderation.setAdvertisement(advertisementService.getInnerAdvertisementByIdForAdmin(adId));
        moderationRepository.save(moderation);
    }

    public UserInnerResponse getUserInner(Long userId) {
        return userService.getUserInner(userId);
    }

    public Advertisement getInnerAdvertisement(Long adId) {
        return advertisementService.getInnerAdvertisementByIdForAdmin(adId);
    }

    public List<ModerationResponse> getAllModeration() {
        return moderationRepository.findAll().stream().map(moderationMapper::getModerationResponseFromModeration).toList();
    }

    public void deleteUser(Long userId) {
        userService.deleteUserById(userId);
    }

    public void deleteAdvertisement(Long adId) {
        advertisementService.deleteForAdmin(adId);
    }

    public List<Payment> getAllPaymentsOfUser(Long userId) {
        return paymentService.getPaymentsOfUser(userId);
    }

    public void rejectAdvertisement(Long adId, Admin admin, String message) {
        Moderation moderation = new Moderation();
        Advertisement advertisement = advertisementService.getInnerAdvertisementByIdForAdmin(adId);
        moderation.setAdvertisement(advertisement);
        moderation.setAdmin(admin);
        moderation.setMessage(message);
        moderationRepository.save(moderation);
    }

    public List<ModerationResponse> getModerationOfUser(UserPrincipal userPrincipal) {
        UserForAd userForAd = userService.getUserForAdById(userPrincipal.getId());
        List<Moderation> moderation = moderationRepository.findModerationByAdvertisement_User(userForAd);
        return moderation.stream()
                .map(moderationMapper::getModerationResponseFromModeration)
                .toList();
    }

    public void saveNewAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    public List<Advertisement> getAllAdvertisementsOfUser(Long userId) {
        return advertisementService.getAllOfUser(userId);
    }

    public List<ModerationResponse> getAllModerationByUser(Long userId) {
        return moderationRepository.findModerationByAdvertisement_UserId(userId)
                .stream().map(moderationMapper::getModerationResponseFromModeration).toList();
    }

    public void deleteImage(Long adId, String imageUuid) throws IOException {
        imageService.deleteImageForAdmin(adId, imageUuid);
    }
}