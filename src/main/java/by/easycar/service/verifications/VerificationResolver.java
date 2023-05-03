package by.easycar.service.verifications;

import by.easycar.exceptions.VerifyException;
import by.easycar.exceptions.VerifyMethodNotSupportedException;
import by.easycar.model.user.UserPrivate;
import by.easycar.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationResolver {
    private final Map<String, VerificationService> verificationServicesMap;
    public static Map<Long, VerifyModel> codesWithMethods = new HashMap<>();
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final UserService userService;

    public boolean verify(Long userId, String code) {
        VerifyModel verifyModel;
        try {
            verifyModel = codesWithMethods.get(userId);
        } catch (Throwable e) {
            throw new VerifyException("You don`t send request for verify.");
        }
        if (verifyModel.verificationService.equals(SmsUserVerificationService.class)) {
            if (verifyModel.code.equals(code)) {
                userService.setVerifiedByPhone(userId);
            }
        } else if (verifyModel.verificationService.equals(EmailUserVerificationService.class)) {
            if (verifyModel.code.equals(code)) {
                userService.setVerifiedByEmail(userId);
            }
        } else if (verifyModel.verificationService.equals(VerifyStub.class)) {
            if (verifyModel.code.equals(code)) {
                userService.setVerifiedByEmail(userId);
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean sendMessage(Long userId, String verificationType) {
        String rawCode = userId + "superSecretKey" + Math.random();
        String code = encoder.encodeToString(rawCode.getBytes());

        UserPrivate userPrivate = userService.getById(userId);

        String serviceType = this.getNameOfService(verificationType);
        var verifyTypeService = verificationServicesMap.get(serviceType);
        codesWithMethods.put(userId, new VerifyModel(verifyTypeService.getClass(), code));
        verifyTypeService.sendMessage(userPrivate, code);
        return true;
    }

    private String getNameOfService(String verificationType) {
        return verificationServicesMap
                .keySet().stream()
                .filter(type -> type.equals(verificationType))
                .findFirst()
                .orElseThrow(() -> new VerifyMethodNotSupportedException("Incorrect type of verification."));
    }

    @Getter
    @AllArgsConstructor
    private class VerifyModel {
        private Class<? extends VerificationService> verificationService;
        private String code;
    }
}
