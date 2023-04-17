package by.easycar.repository;

import by.easycar.model.administration.Moderation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModerationRepository extends JpaRepository<Moderation, Long> {
}
