package by.easycar.service.mappers;

import by.easycar.model.administration.Moderation;
import by.easycar.model.dto.ModerationResponse;
import org.springframework.stereotype.Component;

@Component
public class ModerationMapper {

    public ModerationResponse getModerationResponseFromModeration(Moderation moderation) {
        ModerationResponse moderationResponse = new ModerationResponse();
        moderationResponse.setId(moderation.getId());
        moderationResponse.setMessage(moderation.getMessage());
        moderationResponse.setAdminId(moderation.getAdmin().getId());
        moderationResponse.setCreationDate(moderation.getCreationDate());
        moderationResponse.setAdvertisementId(moderation.getAdvertisement().getId());
        return moderationResponse;
    }
}