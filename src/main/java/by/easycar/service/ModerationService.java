package by.easycar.service;

import by.easycar.model.administration.Moderation;
import by.easycar.repository.AdminRepository;
import by.easycar.repository.ModerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModerationService {

    private final ModerationRepository moderationRepository;
    private final AdminRepository adminRepository;
    private final UserService userService;

    public Moderation getById(long id) {
        return moderationRepository.findById(id).orElseThrow();
    }

    public List<Moderation> getAll() {
        return moderationRepository.findAll();
    }

    public void saveModeration(Long userId, Long adminId, String message) {
        Moderation moderation = new Moderation();
        moderation.setAdmin(adminRepository.findById(adminId).orElseThrow());
        moderation.setMessage(message);
        moderationRepository.save(moderation);
    }
}
