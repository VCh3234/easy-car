package by.easycar.service;

import by.easycar.model.administration.Admin;
import by.easycar.model.administration.Moderation;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.repository.ModerationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdvertisementService advertisementService;

    private final ModerationRepository moderationRepository;

    @Autowired
    public AdminService(AdvertisementService advertisementService, ModerationRepository moderationRepository) {
        this.advertisementService = advertisementService;
        this.moderationRepository = moderationRepository;
    }

    public List<Advertisement> getAllAdvertisementsNotModerated() {
        return advertisementService.getAllNotModeratedAdvertisement();
    }

    @Transactional
    public boolean accept(Long adId) {
        if(advertisementService.accept(adId)) {
            Moderation moderation = new Moderation();
            moderation.setMessage("Accept advertisement");
            moderation.setAdmin((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            moderation.setAdvertisement(advertisementService.getInnerAdvertisementById(adId));
            moderationRepository.save(moderation);
            return true;
        } else {
            return false;
        }
    }
}
