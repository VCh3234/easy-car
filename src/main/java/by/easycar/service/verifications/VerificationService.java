package by.easycar.service.verifications;

import by.easycar.model.user.UserPrivate;

public interface VerificationService {
    void sendMessage(UserPrivate userPrivate, String code);
}
