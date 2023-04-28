package by.easycar.service;

import by.easycar.exceptions.user.SaveUserDataException;
import by.easycar.exceptions.user.UniqueEmailException;
import by.easycar.exceptions.user.UniquePhoneNumberException;
import by.easycar.exceptions.user.UserFindException;
import by.easycar.model.user.UserForAd;
import by.easycar.model.user.UserInnerRequest;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserRequest;
import by.easycar.repository.UserForAdRepository;
import by.easycar.repository.UserRepository;
import by.easycar.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserForAdRepository userForAdRepository;

    public UserPrivate getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
    }

    public UserInnerRequest getUserInner(Long id) {
        UserPrivate userPrivate = this.getById(id);
        UserInnerRequest userInnerRequest = userMapper.getUserInnerFromUserPrivate(userPrivate);
        return userInnerRequest;
    }

    public boolean deleteUserById(long id) {
        userRepository.deleteById(id);
        return true;
    }

    public boolean saveNewUser(UserRequest newUser, String rawPassword) {
        UserPrivate userPrivate = userMapper.getUserPrivateFromUserRequest(newUser, passwordEncoder.encode(rawPassword));
        return saveData(userPrivate);
    }

    public boolean updateUser(UserRequest userRequest, Long id) {
        UserPrivate userPrivate = this.getById(id);
        if (userRequest.getPhoneNumber() != null) {
            userPrivate.setPhoneNumber(userRequest.getPhoneNumber());
            userPrivate.setVerifiedByPhone(false);
        }
        if (userRequest.getEmail() != null) {
            userPrivate.setEmail(userRequest.getEmail());
            userPrivate.setVerifiedByEmail(false);
        }
        if (userRequest.getName() != null) {
            userPrivate.setName(userRequest.getName());
        }
        return this.saveData(userPrivate);
    }

    private boolean saveData(UserPrivate userPrivate) {
        try {
            userRepository.save(userPrivate);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            if (message.contains("uk_email")) {
                throw new UniqueEmailException("User already exists with email: " + userPrivate.getEmail());
            } else if (message.contains("uk_phone")) {
                throw new UniquePhoneNumberException("User already exists with phone: " + userPrivate.getPhoneNumber());
            } else if (message.contains("u_email")) {
                throw new SaveUserDataException("Email must be not null");
            } else if (message.contains("u_phone")) {
                throw new SaveUserDataException("Phone must be not null");
            }
            return false;
        }
        return true;
    }

    @Deprecated
    private boolean isEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueEmailException("User already exist with email:" + email);
        }
        return true;
    }

    @Deprecated
    private boolean isPhoneNumberUnique(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UniquePhoneNumberException("User already exist with phone number:" + phoneNumber);
        }
        return true;
    }

    public UserForAd getUserForAdById(Long id) {
        return userForAdRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
    }

    public UserForAd getUserForAdFromUserPrivate(UserPrivate userPrivate) {
        return userMapper.getUserForAdFromUserPrivate(userPrivate);
    }

    public boolean updatePassword(String password, Long id) {
        UserPrivate userPrivate = this.getById(id);
        userPrivate.setPassword(passwordEncoder.encode(password));
        return this.saveData(userPrivate);
    }
}
