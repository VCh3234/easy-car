package by.easycar.repository;

import by.easycar.model.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByModerated(boolean b);
}
