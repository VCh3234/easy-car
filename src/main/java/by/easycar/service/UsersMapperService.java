package by.easycar.service;

import by.easycar.model.user.UserInner;
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

    public UserInner getUserInnerFromUserPrivate(UserPrivate userPrivate) {
        UserInner userInner = new UserInner();
        userInner.setEmail(userPrivate.getEmail());
        userInner.setId(userPrivate.getId());
        userInner.setStatus(userPrivate.getStatus());
        userInner.setName(userPrivate.getName());
        userInner.setPhoneNumber(userPrivate.getPhoneNumber());
        userInner.setCreationDate(userPrivate.getCreationDate());
        userInner.setUpdateTime(userPrivate.getUpdateTime());
        userInner.setUps(userPrivate.getUps());
        userInner.setVerifiedByPhone(userPrivate.isVerifiedByPhone());
        userInner.setVerifiedByEmail(userPrivate.isVerifiedByPhone());
        return userInner;
    }

    public UserPrivate getUserPrivateFromNewUserRequest(UserRequest userRequest) {
        UserPrivate userPrivate = new UserPrivate();
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        userPrivate.setPassword(encodedPassword);
        userPrivate.setPhoneNumber(userRequest.getPhoneNumber());
        userPrivate.setEmail(userRequest.getEmail());
        userPrivate.setName(userRequest.getName());
        return userPrivate;
    }
}
