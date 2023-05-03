package by.easycar.service.verifications;

import by.easycar.exceptions.ForVerifyStubException;
import by.easycar.model.user.UserPrivate;
import org.springframework.stereotype.Component;

@Component("verify_stub")
public class VerifyStub implements VerificationService {

    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
        throw new ForVerifyStubException(code);
    }
}
