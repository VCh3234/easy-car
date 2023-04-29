package by.easycar.service.mappers;

import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserInnerRequest;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
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
        userForAd.setEmail(userPrivate.getEmail());
        userForAd.setId(userPrivate.getId());
        userForAd.setName(userPrivate.getName());
        userForAd.setPhoneNumber(userPrivate.getPhoneNumber());
        return userForAd;
    }
}
