package by.easycar.service.verification;

import by.easycar.exceptions.VerifyException;
import by.easycar.exceptions.VerifyMethodNotSupportedException;
import by.easycar.service.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class VerificationResolver {

    private final Map<String, VerificationService> MAP_OF_VERIFY_SERVICES;

    private Map<String, VerifyModel> MESSAGES = new ConcurrentHashMap<>();

    private final Base64.Encoder ENCODER = Base64.getEncoder();

    private final UserService userService;

    @Autowired
    public VerificationResolver(Map<String, VerificationService> MAP_OF_VERIFY_SERVICES, UserService userService) {
        this.MAP_OF_VERIFY_SERVICES = MAP_OF_VERIFY_SERVICES;
        this.userService = userService;
    }

    @AllArgsConstructor
    private static class VerifyModel {

        private VerificationService verificationService;

        private Long id;

        private LocalDateTime localDateTime;
    }

    public void verify(String code) {
        VerifyModel verifyModel = null;
        try {
            verifyModel = MESSAGES.get(code);
        } catch (NullPointerException ignored) {
        }
        if (verifyModel == null) {
            throw new VerifyException("Wrong code.");
        }
        if (verifyModel.verificationService instanceof SmsUserVerificationService) {
            userService.setVerifiedByPhone(verifyModel.id);
        } else if (verifyModel.verificationService instanceof EmailUserVerificationService) {
            userService.setVerifiedByEmail(verifyModel.id);
        } else {
            throw new VerifyException("Can't verify user.");
        }
        MESSAGES.remove(code);
    }

    public void sendMessage(Long userId, String verificationType) throws MessagingException {
        VerificationService verificationService = null;
        try {
            verificationService = MAP_OF_VERIFY_SERVICES.get(verificationType);
        } catch (NullPointerException ignored) {
        }
        if (verificationService == null) {
            throw new VerifyMethodNotSupportedException("Unsupported type of service.");
        }
        String code = getRandomCode();
        VerifyModel verifyModel = new VerifyModel(verificationService, userId, LocalDateTime.now());
        if (!MESSAGES.containsKey(code)) {
            MESSAGES.put(code, verifyModel);
        } else {
            sendMessage(userId, verificationType);
            return;
        }
        verificationService.sendMessage(userService.getById(userId), code);
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    void deleteOldMessages() {
        MESSAGES = MESSAGES.entrySet()
                .stream().filter((x) -> {
                    LocalDateTime ldt = x.getValue().localDateTime;
                    return !ldt.isBefore(LocalDateTime.now().minusHours(2));
                }).collect(Collectors.toConcurrentMap((Map.Entry::getKey), (Map.Entry::getValue)));
    }

    private String getRandomCode() {
        String rawCode = Math.random() + "" + Math.random();
        return ENCODER.encodeToString(rawCode.getBytes());
    }
}