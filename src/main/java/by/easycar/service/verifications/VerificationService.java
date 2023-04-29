package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;

@SuppressWarnings("SameReturnValue")
public interface VerificationService {
    void sendMessage(UserPrivate userPrivate, String code);
}
