package by.easycar.service.mappers;

import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.Vehicle;
import by.easycar.requests.AdvertisementRequest;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementMapper {

    public Advertisement getAdvertisementFromAdvertisementRequest(AdvertisementRequest advertisementRequest) {
        Advertisement advertisement = new Advertisement();
        advertisement.setPrice(advertisementRequest.getPrice());
        advertisement.setVINNumber(advertisementRequest.getVinNumber());
        advertisement.setDescription(advertisementRequest.getDescription());
        advertisement.setRegion(advertisementRequest.getRegion());
        advertisement.setMileage(advertisementRequest.getMileage());
        advertisement.setEngineCapacity(advertisementRequest.getEngineCapacity());
        advertisement.setEngineType(advertisementRequest.getEngineType());
        advertisement.setTransmissionType(advertisementRequest.getTransmissionType());
        advertisement.setCarYear(advertisementRequest.getCarYear());
        Vehicle vehicle = new Vehicle();
        vehicle.setBodyType(advertisementRequest.getBodyType());
        vehicle.setBrand(advertisementRequest.getBrand());
        vehicle.setModel(advertisementRequest.getModel());
        vehicle.setGeneration(advertisementRequest.getGeneration());
        advertisement.setVehicle(vehicle);
        return advertisement;
    }

    public Advertisement setUpdates(Advertisement oldAdvertisement, AdvertisementRequest advertisementRequest) {
        oldAdvertisement.setPrice(advertisementRequest.getPrice());
        oldAdvertisement.setVINNumber(advertisementRequest.getVinNumber());
        oldAdvertisement.setDescription(advertisementRequest.getDescription());
        oldAdvertisement.setRegion(advertisementRequest.getRegion());
        oldAdvertisement.setMileage(advertisementRequest.getMileage());
        oldAdvertisement.setEngineCapacity(advertisementRequest.getEngineCapacity());
        oldAdvertisement.setEngineType(advertisementRequest.getEngineType());
        oldAdvertisement.setTransmissionType(advertisementRequest.getTransmissionType());
        oldAdvertisement.setCarYear(advertisementRequest.getCarYear());
        Vehicle vehicle = new Vehicle();
        vehicle.setBodyType(advertisementRequest.getBodyType());
        vehicle.setBrand(advertisementRequest.getBrand());
        vehicle.setModel(advertisementRequest.getModel());
        vehicle.setGeneration(advertisementRequest.getGeneration());
        oldAdvertisement.setVehicle(vehicle);
        return oldAdvertisement;
    }
}