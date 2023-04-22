package by.easycar.service;

import by.easycar.exceptions.CreationUserException;
import by.easycar.exceptions.UpdatingUserException;
import by.easycar.exceptions.UserFindException;
import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
import by.easycar.model.user.UsersMapper;
import by.easycar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserPrivate getById(long id) {
        return userRepository.findById(id).orElseThrow(); //TODO: security
    }


    public void saveNewUser(UserPrivate user) {
        if (isNew(user)) {
            persistUser(user);
        } else {
            throw new CreationUserException("Request must be without 'id' field.");
        }
    }

    public void updateUser(UserPrivate user) {
        if (!isNew(user)) {
            persistUser(user);
        } else {
            throw new UpdatingUserException("Request must be with 'id' field.");
        }
    }

    public UserPublic getUserPublic(Long id) {
        UserPrivate userPrivate = userRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
        UserPublic userPublic = UsersMapper.getUserPublicFromUserPrivate(userPrivate);
        return userPublic;
    }

    public UserInner getUserInner(Long id) {
        UserPrivate userPrivate = userRepository.findById(id).orElseThrow(() -> new UserFindException("Can`t find user with id: " + id));
        UserInner userInner = UsersMapper.getUserInnerFromUserPrivate(userPrivate);
        return userInner;
    }

    private boolean isNew(UserPrivate userPrivate) {
        return userPrivate.getId() == null || userPrivate.getId() == 0;
    }

    private void persistUser(UserPrivate userPrivate) {
        if(userRepository.existsByEmail(userPrivate.getEmail())) {
            throw new CreationUserException("User already exist with email: " + userPrivate.getEmail());
        } else if (userRepository.existsByPhoneNumber(userPrivate.getPhoneNumber())) {
            throw new CreationUserException("User already exist with phone: " + userPrivate.getPhoneNumber());
        }
        userRepository.save(userPrivate);
    }



    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public Set<UserInner> getAllByIsChecked() {
        Set<UserInner> result = userRepository.findAllByIsChecked(false).stream()
                .map(UsersMapper::getUserInnerFromUserPrivate)
                .collect(Collectors.toSet());
        return result;
    }

    public void acceptUserWithId(Long id) {
        userRepository.acceptUserWithId(id);
    }
}
