package by.easycar.service;

import by.easycar.exceptions.advertisement.FindAdvertisementException;
import by.easycar.exceptions.advertisement.VerifyException;
import by.easycar.exceptions.advertisement.WrongUserException;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.security.UserSecurity;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.AdvertisementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserService userService;

    public List<Advertisement> getAllModeratedAdvertisement() {
        return advertisementRepository.findAllByModerated(true);
    }

    public List<Advertisement> getAllNotModeratedAdvertisement() {
        return advertisementRepository.findAllByModerated(false);
    }

    @Transactional
    public boolean saveNewAd(Advertisement advertisement, UserSecurity user) {
        UserPrivate userPrivate = userService.getById(user.getId());
        if (userPrivate.isVerifiedByEmail() || userPrivate.isVerifiedByPhone()) {
            UserForAd userForAd = userService.getUserForAdFromUserPrivate(userPrivate);
            userPrivate.getAdvertisements().add(advertisement);
            advertisement.setUser(userForAd);
            advertisementRepository.save(advertisement);
        } else {
            throw new VerifyException("User doesn't verified");
        }
        return true;
    }

    public void update(Advertisement advertisement, UserSecurity user) {
        Advertisement oldAd = advertisementRepository
                .findById(advertisement.getId())
                .orElseThrow(() -> new FindAdvertisementException("Can`t find ad with id: " + advertisement.getId()));
        if (!oldAd.getUser().getId().equals(user.getId())) {
            throw new WrongUserException("User id doesn't match.");
        } else {
            advertisement.setViewsCount(oldAd.getViewsCount());
            advertisementRepository.save(advertisement);
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
}
