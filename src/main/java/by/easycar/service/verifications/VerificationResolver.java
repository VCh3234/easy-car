package by.easycar.service.verifications;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationResolver {
    private final Map<String, VerificationService> verificationServicesMap;

    public boolean verify(long id, String verificationType) {
        var serviceType = verificationServicesMap
                .keySet().stream()
                .filter(type -> type.equals(verificationType))
                .findFirst()
                .orElseThrow(() -> new VerifyMethodNotSupportedException("Incorrect type of verification"));
        return verificationServicesMap.get(serviceType).verify(id);
    }
}
