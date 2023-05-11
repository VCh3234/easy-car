package by.easycar.service;

import by.easycar.exceptions.user.SaveUserDataException;
import by.easycar.exceptions.user.UserFindException;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.model.dto.user.UserRegisterRequest;
import by.easycar.model.dto.user.UserRequest;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.UserForAdRepository;
import by.easycar.repository.UserRepository;
import by.easycar.service.mappers.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private UserForAdRepository userForAdRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, UserForAdRepository userForAdRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userForAdRepository = userForAdRepository;
    }

    public UserService() {
    }

    public UserPrivate getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserFindException("Can`t find user with id: " + userId));
    }

    public UserInnerResponse getUserInner(Long userId) {
        UserPrivate userPrivate = this.getById(userId);
        return userMapper.getUserInnerFromUserPrivate(userPrivate);
    }

    @Transactional
    public void deleteUserById(Long id) {
        UserPrivate userPrivate = this.getById(id);
        userPrivate.getAdvertisements().forEach(advertisement-> ImageService.deleteDir(advertisement.getId()));
        userRepository.deleteById(id);
    }

    public void saveNewUser(UserRegisterRequest userRegisterRequest) {
        String password = passwordEncoder.encode(userRegisterRequest.getPasswordRequest().getPassword());
        UserPrivate userPrivate = userMapper.getUserPrivateFromUserRegisterRequest(userRegisterRequest.getUserRequest(), password);
        userRepository.save(userPrivate);
    }

    public void updateUser(UserRequest userRequest, Long id) {
        UserPrivate userPrivate = this.getById(id);
        if (userRequest == null) {
            throw new SaveUserDataException("UserRequest is null.");
        }
        boolean isUpdated = false;
        if (!userPrivate.getPhoneNumber().equals(userRequest.getPhoneNumber())) {
            userPrivate.setPhoneNumber(userRequest.getPhoneNumber());
            userPrivate.setVerifiedByPhone(false);
            isUpdated = true;
        }
        if (!userPrivate.getEmail().equals(userRequest.getEmail())) {
            userPrivate.setEmail(userRequest.getEmail());
            userPrivate.setVerifiedByEmail(false);
            isUpdated = true;
        }
        if (!userPrivate.getName().equals(userRequest.getName())) {
            userPrivate.setName(userRequest.getName());
            isUpdated = true;
        }
        if (isUpdated) {
            userRepository.save(userPrivate);
        } else {
            throw new SaveUserDataException("Nothing to update.");
        }
    }

    public UserForAd getUserForAdFromUserPrivate(UserPrivate userPrivate) {
        return userMapper.getUserForAdFromUserPrivate(userPrivate);
    }

    public void updatePassword(String newPassword, Long userId) {
        UserPrivate userPrivate = this.getById(userId);
        userPrivate.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userPrivate);
    }

    public void setVerifiedByPhone(Long id) {
        UserPrivate userPrivate = this.getById(id);
        userPrivate.setVerifiedByPhone(true);
        userRepository.save(userPrivate);
    }

    public void setVerifiedByEmail(Long id) {
        UserPrivate userPrivate = this.getById(id);
        userPrivate.setVerifiedByEmail(true);
        userRepository.save(userPrivate);
    }

    public void saveChanges(UserPrivate user) {
        userRepository.save(user);
    }

    public UserForAd getUserForAdById(Long userId) {
        return userForAdRepository.findById(userId).orElseThrow(() -> new UserFindException("Can`t find user with id: " + userId));
    }
}