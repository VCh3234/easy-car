package by.easycar.service.verification;

import by.easycar.model.user.UserPrivate;
import jakarta.mail.MessagingException;

public interface VerificationService {

    void sendMessage(UserPrivate userPrivate, String code) throws MessagingException;
}