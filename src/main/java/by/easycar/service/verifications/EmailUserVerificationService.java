package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;
import org.springframework.stereotype.Component;

@Component("verify_email")
public class EmailUserVerificationService implements VerificationService {

    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
    }
}