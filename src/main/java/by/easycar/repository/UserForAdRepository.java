package by.easycar.repository;

import by.easycar.model.user.UserForAd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserForAdRepository extends JpaRepository<UserForAd, Long> {
}
