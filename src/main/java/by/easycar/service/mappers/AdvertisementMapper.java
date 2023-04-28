package by.easycar.service.mappers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.AdvertisementRequest;

public class AdvertisementMapper {
    public Advertisement getAdvertisementFromAdvertisementRequest(AdvertisementRequest advertisementRequest) {
        Advertisement advertisement = new Advertisement();
        advertisement.setPrice(advertisementRequest.getPrice());
        advertisement.setVINNumber(advertisementRequest.getVINNumber());
        advertisement.setDescription(advertisementRequest.getDescription());
        advertisement.setRegion(advertisementRequest.getRegion());
        advertisement.setMileage(advertisementRequest.getMileage());
        advertisement.setEngineCapacity(advertisementRequest.getEngineCapacity());
        advertisement.setEngineType(advertisementRequest.getEngineType());
        advertisement.setTransmissionType(advertisementRequest.getTransmissionType());
        advertisement.setVehicle(advertisementRequest.getVehicle());
        return advertisement;
    }

    public Advertisement setUpdates(Advertisement oldAdvertisement, AdvertisementRequest advertisementRequest) {
        oldAdvertisement.setPrice(advertisementRequest.getPrice());
        oldAdvertisement.setVINNumber(advertisementRequest.getVINNumber());
        oldAdvertisement.setDescription(advertisementRequest.getDescription());
        oldAdvertisement.setRegion(advertisementRequest.getRegion());
        oldAdvertisement.setMileage(advertisementRequest.getMileage());
        oldAdvertisement.setEngineCapacity(advertisementRequest.getEngineCapacity());
        oldAdvertisement.setEngineType(advertisementRequest.getEngineType());
        oldAdvertisement.setTransmissionType(advertisementRequest.getTransmissionType());
        oldAdvertisement.setVehicle(advertisementRequest.getVehicle());
        return oldAdvertisement;
    }
}
