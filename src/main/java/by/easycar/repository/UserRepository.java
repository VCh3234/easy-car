package by.easycar.repository;

import by.easycar.model.user.UserPrivate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserPrivate, Long> {

    Optional<UserPrivate> findByEmail(String email);
}