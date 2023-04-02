package by.easycar.repository;

import by.easycar.model.NewUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewUserDTORepository extends JpaRepository<NewUserDTO, Long> {
}