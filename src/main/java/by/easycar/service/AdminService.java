package by.easycar.service;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.user.UserInnerResponse;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrincipal;
import by.easycar.repository.ModerationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdvertisementService advertisementService;

    private final ModerationRepository moderationRepository;

    private final UserService userService;

    private final PaymentService paymentService;

    @Autowired
    public AdminService(AdvertisementService advertisementService, ModerationRepository moderationRepository, UserService userService, PaymentService paymentService) {
        this.advertisementService = advertisementService;
        this.moderationRepository = moderationRepository;
        this.userService = userService;
        this.paymentService = paymentService;
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

    public List<Moderation> getAllModeration() {
        return moderationRepository.findAll();
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

    public List<Moderation> getModerationsOfUser(UserPrincipal userPrincipal) {
        UserForAd userForAd = userService.getUserForAdById(userPrincipal.getId());
        return moderationRepository.findAllByAdvertisementUserContaining(userForAd);
    }
}