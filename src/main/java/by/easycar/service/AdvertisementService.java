package by.easycar.service;

import by.easycar.exceptions.VerifyException;
import by.easycar.exceptions.advertisement.FindAdvertisementException;
import by.easycar.exceptions.advertisement.WrongUserException;
import by.easycar.exceptions.user.UpException;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.AdvertisementRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrincipal;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.AdvertisementRepository;
import by.easycar.repository.VehicleRepository;
import by.easycar.service.mappers.AdvertisementMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final VehicleRepository vehicleRepository;

    private final UserService userService;

    private final AdvertisementMapper advertisementMapper;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, VehicleRepository vehicleRepository, UserService userService, AdvertisementMapper advertisementMapper) {
        this.advertisementRepository = advertisementRepository;
        this.vehicleRepository = vehicleRepository;
        this.userService = userService;
        this.advertisementMapper = advertisementMapper;
    }

    public List<Advertisement> getAllNotModeratedAdvertisement() {
        return advertisementRepository.findAllByModerated(false);
    }

    @Transactional
    public void saveNewAd(AdvertisementRequest advertisementRequest, Long userId) {
        UserPrivate userPrivate = userService.getById(userId);
        if (userPrivate.isVerifiedByEmail() || userPrivate.isVerifiedByPhone()) {
            Advertisement advertisement = advertisementMapper.getAdvertisementFromAdvertisementRequest(advertisementRequest);
            UserForAd userForAd = userService.getUserForAdFromUserPrivate(userPrivate);
            advertisement.setUser(userForAd);
            vehicleRepository.save(advertisement.getVehicle());
            advertisementRepository.save(advertisement);
        } else {
            throw new VerifyException("User doesn't verified");
        }
    }

    @Transactional
    public void update(Long adId, AdvertisementRequest advertisementRequest, UserPrincipal user) {
        Advertisement oldAdvertisement = advertisementRepository
                .findById(adId)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find ad with id: " + adId));
        this.checkAuthorities(oldAdvertisement, user.getId());
        Advertisement updatedAdvertisement = advertisementMapper.setUpdates(oldAdvertisement, advertisementRequest);
        vehicleRepository.save(updatedAdvertisement.getVehicle());
        advertisementRepository.save(updatedAdvertisement);

    }

    @Transactional
    public void delete(Long adId, UserPrincipal user) {
        Advertisement oldAd = advertisementRepository
                .findById(adId)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find ad with id: " + adId));
        this.checkAuthorities(oldAd, user.getId());
        ImageService.deleteDir(adId);
        advertisementRepository.deleteById(adId);

    }

    public List<Advertisement> getAllOfUser(Long userId) {
        return userService.getById(userId).getAdvertisements();
    }

    public Advertisement getInnerAdvertisementById(Long adId, Long userId) {
        Advertisement advertisement = advertisementRepository.findById(adId).orElseThrow(() -> new FindAdvertisementException("Can`t find advertisement with id: " + adId));
        this.checkAuthorities(advertisement, userId);
        return advertisement;
    }

    public Advertisement getPublicById(Long adId) {
        return advertisementRepository.findByIdAndModerated(adId, true)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find advertisement with id: " + adId + ", or ad isn't moderated."));
    }

    public boolean acceptModeration(Long adId) {
        return advertisementRepository.acceptModeration(adId) == 1;
    }

    public void saveChanges(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }

    public List<Advertisement> getAllModeratedAdvertisementOrdered() {
        return advertisementRepository.findAllByModeratedOrderByUpTimeDesc(true);
    }

    @Transactional
    public void upAdvertisement(Long adId, Long userId) {
        UserPrivate user = userService.getById(userId);
        Advertisement advertisement = this.getPublicById(adId);
        this.checkAuthorities(advertisement, user);
        if (user.getUps() > 0) {
            advertisement.setUpTime(LocalDateTime.now());
            user.setUps(user.getUps() - 1);
            this.saveChanges(advertisement);
            userService.saveChanges(user);
        } else {
            throw new UpException("You don't have enough ups.");
        }
    }

    private void checkAuthorities(Advertisement advertisement, UserPrivate user) {
        this.checkAuthorities(advertisement, user.getId());
    }

    private void checkAuthorities(Advertisement advertisement, Long userId) {
        if (!userId.equals(advertisement.getUser().getId())) {
            throw new WrongUserException("User id doesn't match.");
        }
    }

    public Advertisement getInnerAdvertisementByIdForAdmin(Long adId) {
        return advertisementRepository.findById(adId)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find advertisement with id: " + adId + ", or ad isn't moderated."));
    }

    public void deleteForAdmin(Long adId) {
        ImageService.deleteDir(adId);
        advertisementRepository.deleteById(adId);
    }
}