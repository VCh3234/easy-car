package by.easycar.repository;

import by.easycar.model.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {

    List<Advertisement> findAllByModeratedOrderByUpTimeDesc(boolean b);

    List<Advertisement> findAllByModerated(boolean b);

    Optional<Advertisement> findByIdAndModerated(Long id, boolean b);

    @Modifying
    @Query(value = "UPDATE Advertisement a SET a.moderated = true WHERE a.id =:adId")
    int acceptModeration(Long adId);

}
