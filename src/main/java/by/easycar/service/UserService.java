package by.easycar.service;

import by.easycar.repository.NewUserDTORepository;
import by.easycar.repository.UserRepository;
import by.easycar.repository.model.user.NewUserDTO;
import by.easycar.repository.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final NewUserDTORepository newUserDTORepository;

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void saveUser(NewUserDTO user) {
        newUserDTORepository.save(user);
    }

}
