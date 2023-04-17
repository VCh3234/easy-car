package by.easycar.service;

import by.easycar.exceptions.CreationUserException;
import by.easycar.exceptions.UpdatingUserException;
import by.easycar.model.user.UserInner;
import by.easycar.model.user.UserPublic;
import by.easycar.model.user.UsersMapper;
import by.easycar.repository.UserRepository;
import by.easycar.model.user.UserPrivate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (user.getId() != null || user.getId() != 0) {
            throw new CreationUserException("Request must be without 'id' field.");
        }
        userRepository.save(user);
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
