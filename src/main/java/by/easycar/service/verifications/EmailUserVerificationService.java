package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("verify_email")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailUserVerificationService implements VerificationService {
    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
    }
}
