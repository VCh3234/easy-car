package by.easycar.service.verifications;

import by.easycar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("verify_sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsUserVerificationService implements VerificationService { //TODO
    private final UserRepository userRepository;

    @Override
    public boolean verify(Long id) {
        return true;
    }
}
