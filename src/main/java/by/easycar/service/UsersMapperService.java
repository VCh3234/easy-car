package by.easycar.service;

import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserInnerRequest;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersMapperService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersMapperService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserInnerRequest getUserInnerFromUserPrivate(UserPrivate userPrivate) {
        UserInnerRequest userInnerRequest = new UserInnerRequest();
        userInnerRequest.setEmail(userPrivate.getEmail());
        userInnerRequest.setId(userPrivate.getId());
        userInnerRequest.setStatus(userPrivate.getStatus());
        userInnerRequest.setName(userPrivate.getName());
        userInnerRequest.setPhoneNumber(userPrivate.getPhoneNumber());
        userInnerRequest.setCreationDate(userPrivate.getCreationDate());
        userInnerRequest.setUpdateTime(userPrivate.getUpdateTime());
        userInnerRequest.setUps(userPrivate.getUps());
        userInnerRequest.setVerifiedByPhone(userPrivate.isVerifiedByPhone());
        userInnerRequest.setVerifiedByEmail(userPrivate.isVerifiedByPhone());
        userInnerRequest.setPayments(userPrivate.getPayments());
        userInnerRequest.setAdvertisements(userPrivate.getAdvertisements());
        return userInnerRequest;
    }

    public UserPrivate getUserPrivateFromUserRequest(UserRequest userRequest, String password) {
        UserPrivate userPrivate = new UserPrivate();
        userPrivate.setPassword(password);
        userPrivate.setPhoneNumber(userRequest.getPhoneNumber());
        userPrivate.setEmail(userRequest.getEmail());
        userPrivate.setName(userRequest.getName());
        return userPrivate;
    }

    public UserForAd getUserForAdFromUserPrivate(UserPrivate userPrivate) {
        UserForAd userForAd = new UserForAd();
        userForAd.setEmail(userForAd.getEmail());
        userForAd.setId(userPrivate.getId());
        userForAd.setName(userPrivate.getName());
        userForAd.setPhoneNumber(userPrivate.getPhoneNumber());
        return userForAd;
    }
}
