package by.easycar.service.verifications;

import by.easycar.exceptions.ForVerifyStubException;
import by.easycar.model.user.UserPrivate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("verify_stub")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerifyStub implements VerificationService {
    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
        throw new ForVerifyStubException(code);
    }
}
