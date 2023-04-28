package by.easycar.repository;

import by.easycar.model.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByModerated(boolean b);

    Optional<Advertisement> findByIdAndModerated(Long id, boolean b);
}
