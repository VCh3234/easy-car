package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;
import org.springframework.stereotype.Component;

@Component("verify_sms")
public class SmsUserVerificationService implements VerificationService {

    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
    }
}
