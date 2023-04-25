package by.easycar.service;

import by.easycar.exceptions.CreationUserException;
import by.easycar.exceptions.UniqueEmailException;
import by.easycar.exceptions.UniquePhoneNumberException;
import by.easycar.exceptions.UserFindException;
import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
import by.easycar.model.user.UserRequest;
import by.easycar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final UsersMapperService usersMapperService;

    public UserPrivate getById(long id) {
        return userRepository.findById(id).orElseThrow(); //TODO: security
    }

    public UserPublic getUserPublic(Long id) {
        UserPrivate userPrivate = userRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
        UserPublic userPublic = usersMapperService.getUserPublicFromUserPrivate(userPrivate);
        return userPublic;
    }

    public UserInner getUserInner(Long id) {
        UserPrivate userPrivate = userRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
        UserInner userInner = usersMapperService.getUserInnerFromUserPrivate(userPrivate);
        return userInner;
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void saveNewUser(UserRequest newUser) {
        UserPrivate userPrivate = usersMapperService.getUserPrivateFromNewUserRequest(newUser);
        try {
            userRepository.save(userPrivate);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            if(message.contains("uk_email")) {
                throw new UniqueEmailException("User already exists with email: " + userPrivate.getEmail());
            } else if (message.contains("uk_phone")) {
                throw new UniquePhoneNumberException("User already exists with phone: " + userPrivate.getPhoneNumber());
            } else {
                throw new CreationUserException(message);
            }
        }
    }

    public void updateUser(UserRequest userRequest, Long id) {

    }

    private boolean isEmailUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UniqueEmailException("User already exist with email:" + email);
        }
        return true;
    }

    private boolean isPhoneNumberUnique(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UniquePhoneNumberException("User already exist with phone number:" + phoneNumber);
        }
        return true;
    }


}
