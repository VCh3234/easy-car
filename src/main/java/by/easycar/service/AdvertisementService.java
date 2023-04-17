package by.easycar.service;

import by.easycar.exceptions.UpdateAdvertisementException;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public void save(Advertisement advertisement) {
        if(advertisement.getId() != 0) {
            throw new UpdateAdvertisementException("Body must be without id field.");
        }
        advertisementRepository.save(advertisement);
    }


    public void update(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }

    public void delete(Long id) {
        advertisementRepository.deleteById(id);
    }

    public List<Advertisement> getAllAdvertisement() {
        return advertisementRepository.findAll();
    }
}
