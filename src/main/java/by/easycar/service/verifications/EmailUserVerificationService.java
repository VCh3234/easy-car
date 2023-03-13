package by.easycar.service.verifications;

import by.easycar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("verify_email")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailUserVerificationService implements VerificationService { //TODO email verification
    private final UserRepository userRepository;

    @Override
    public boolean verify(Long id) {
        return true;
    }
}
