package by.easycar.repository;

import by.easycar.model.administration.Moderation;
import by.easycar.model.user.UserForAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModerationRepository extends JpaRepository<Moderation, Long> {

    List<Moderation> findModerationByAdvertisement_User(UserForAd userForAd);

    List<Moderation> findModerationByAdvertisement_UserId(Long userId);
}