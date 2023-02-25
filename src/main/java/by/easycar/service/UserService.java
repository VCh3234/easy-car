package by.easycar.service;

import by.easycar.repository.UserRepository;
import by.easycar.repository.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        User userResult = userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
