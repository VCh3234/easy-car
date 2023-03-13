package by.easycar.repository;

import by.easycar.repository.model.user.NewUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewUserDTORepository extends JpaRepository<NewUserDTO, Long> {
}