package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("verify_sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsUserVerificationService implements VerificationService {
    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
    }
}
