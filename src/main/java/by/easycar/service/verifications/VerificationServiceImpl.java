package by.easycar.service.verifications;

import by.easycar.repository.UserRepository;
import by.easycar.repository.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationServiceImpl implements VerificationService {
    private final UserRepository userRepository;

    @Override
    public boolean verify(Long id) {
        User user;
        try {
            user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Can`t find user with id  = %d", id)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        user.setStatus(new User.UserStatus(2));
        userRepository.save(user);
    return true;
    }
}
