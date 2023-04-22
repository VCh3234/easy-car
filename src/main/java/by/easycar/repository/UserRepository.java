package by.easycar.repository;

import by.easycar.model.user.UserPrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserPrivate, Long> {
    List<UserPrivate> findAllByIsChecked(Boolean isChecked);

    @Query("UPDATE UserPrivate u SET u.isChecked = true WHERE u.id =:id")
    void acceptUserWithId(Long id);

    Optional<UserPrivate> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);
}
