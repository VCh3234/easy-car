package by.easycar.controllers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.requests.SearchParams;
import by.easycar.service.search.SearchAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/ad")
public class SearchAdvertisementController {

    private final SearchAdvertisementService searchAdvertisementService;

    @Autowired
    public SearchAdvertisementController(SearchAdvertisementService searchAdvertisementService) {
        this.searchAdvertisementService = searchAdvertisementService;
    }

    @PostMapping
    public ResponseEntity<List<Advertisement>> getBySearchCriteria(@RequestBody List<SearchParams> searchParams) {
        List<Advertisement> advertisements = searchAdvertisementService.getAllByParams(searchParams);
        return ResponseEntity.ok(advertisements);
    }
}
