package by.easycar.service;

import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
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
        return userInner;
    }

    public UserPublic getUserPublicFromUserPrivate(UserPrivate userPrivate) {
        UserPublic userPublic = new UserPublic();
        userPublic.setEmail(userPrivate.getEmail());
        userPublic.setId(userPrivate.getId());
        userPublic.setName(userPrivate.getName());
        return userPublic;
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
