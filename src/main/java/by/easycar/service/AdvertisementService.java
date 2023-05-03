package by.easycar.service;

import by.easycar.exceptions.VerifyException;
import by.easycar.exceptions.advertisement.FindAdvertisementException;
import by.easycar.exceptions.advertisement.WrongUserException;
import by.easycar.exceptions.user.UpException;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.AdvertisementRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserSecurity;
import by.easycar.repository.AdvertisementRepository;
import by.easycar.repository.VehicleRepository;
import by.easycar.service.mappers.AdvertisementMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    public boolean saveNewAd(AdvertisementRequest advertisementRequest, Long userId) {
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
        return true;
    }

    public void update(Long adId, AdvertisementRequest advertisementRequest, UserSecurity user) {
        Advertisement oldAdvertisement = advertisementRepository
                .findById(adId)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find ad with id: " + adId));
        if (!oldAdvertisement.getUser().getId().equals(user.getId())) {
            throw new WrongUserException("User id doesn't match.");
        } else {
            Advertisement updatedAdvertisement = advertisementMapper.setUpdates(oldAdvertisement, advertisementRequest);
            vehicleRepository.save(updatedAdvertisement.getVehicle());
            advertisementRepository.save(updatedAdvertisement);
        }
    }

    public void delete(Long adId, UserSecurity user) {
        Advertisement oldAd = advertisementRepository
                .findById(adId)
                .orElseThrow(() -> new FindAdvertisementException("Can`t find ad with id: " + adId));
        if (!oldAd.getUser().getId().equals(user.getId())) {
            throw new WrongUserException("User id doesn't match.");
        } else {
            advertisementRepository.deleteById(adId);
        }
    }

    public Set<Advertisement> getAllOfUser(Long id) {
        Set<Advertisement> advertisements = userService.getById(id).getAdvertisements();
        return advertisements;
    }

    public Advertisement getInnerAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new FindAdvertisementException("Can`t find advertisement with id: " + id));
    }

    public Advertisement getPublicById(Long id) {
        return advertisementRepository.findByIdAndModerated(id, true).orElseThrow(() -> new FindAdvertisementException("Can`t find advertisement with id: " + id + ", or ad isn't moderated."));
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
        if(!user.getId().equals(advertisement.getUser().getId())) {
            throw new WrongUserException("User id doesn't match.");
        }
        if(user.getUps() > 0) {
            advertisement.setUpTime(LocalDateTime.now());
            user.setUps(user.getUps() - 1);
            this.saveChanges(advertisement);
            userService.saveChanges(user);
        } else {
            throw new UpException("You don't have enough ups.");
        }
    }
}
