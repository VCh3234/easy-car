package by.easycar.repository;

import by.easycar.model.user.UserPrivate;
import by.easycar.model.user.UserPrivate.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserPrivate, Long> {
    List<UserPrivate> findAllByIsChecked(Boolean isChecked);

    @Query("UPDATE UserPrivate u SET u.isChecked = true WHERE u.id =:id")
    void acceptUserWithId(Long id);
}
