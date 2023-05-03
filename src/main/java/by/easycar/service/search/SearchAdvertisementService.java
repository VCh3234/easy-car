package by.easycar.service.search;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.SearchParams;
import by.easycar.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchAdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public SearchAdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<Advertisement> getAllByParams(List<SearchParams> searchParams) {
        Specification<Advertisement> advertisementSpecification =
                Specification.where(new AdvertisementSpecification(searchParams.get(0)));
        for (int i = 1; i < searchParams.size(); i++) {
            advertisementSpecification = Specification.where(advertisementSpecification)
                    .and(new AdvertisementSpecification(searchParams.get(i)));
        }
        Sort sort = Sort.by("upTime").descending();
        List<Advertisement> advertisements = advertisementRepository.findAll(advertisementSpecification, sort);
        return advertisements;
    }
}
