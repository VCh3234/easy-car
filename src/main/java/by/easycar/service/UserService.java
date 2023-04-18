package by.easycar.service;

import by.easycar.exceptions.CreationUserException;
import by.easycar.exceptions.UpdatingUserException;
import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPublic;
import by.easycar.model.user.UsersMapper;
import by.easycar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public UserPrivate getById(long id) {
        return userRepository.findById(id).orElseThrow(); //TODO: security
    }


    public void saveNewUser(UserPrivate user) {
        if (user.getId() == null) {
            userRepository.save(user);
        } else if (user.getId() != 0) {
            throw new CreationUserException("Request must be without 'id' field.");
        }
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

    public UserPublic getUserPublic(Long id) {
        UserPublic userPublic = UsersMapper.getUserPublicFromUserPrivate(userRepository.findById(id).orElseThrow());
        return userPublic;
    }

    public void updateUser(UserPrivate user) {
        if (user.getId() == 0 || user.getId() == null) {
            throw new UpdatingUserException("Request must be with 'id' field.");
        }
        userRepository.save(user);
    }
}
