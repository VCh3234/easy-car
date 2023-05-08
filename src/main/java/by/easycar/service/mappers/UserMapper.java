package by.easycar.service.mappers;

import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.dto.user.UserRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserInnerResponse getUserInnerFromUserPrivate(UserPrivate userPrivate) {
        UserInnerResponse userInnerResponse = new UserInnerResponse();
        userInnerResponse.setEmail(userPrivate.getEmail());
        userInnerResponse.setId(userPrivate.getId());
        userInnerResponse.setName(userPrivate.getName());
        userInnerResponse.setPhoneNumber(userPrivate.getPhoneNumber());
        userInnerResponse.setCreationDate(userPrivate.getCreationDate());
        userInnerResponse.setUpdateTime(userPrivate.getUpdateTime());
        userInnerResponse.setUps(userPrivate.getUps());
        userInnerResponse.setVerifiedByPhone(userPrivate.isVerifiedByPhone());
        userInnerResponse.setVerifiedByEmail(userPrivate.isVerifiedByEmail());
        return userInnerResponse;
    }

    public UserPrivate getUserPrivateFromUserRegisterRequest(UserRequest userRequest, String password) {
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