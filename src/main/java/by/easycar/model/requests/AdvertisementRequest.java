package by.easycar.model.requests;

import by.easycar.model.advertisement.Vehicle;
import lombok.Data;

@Data
public class AdvertisementRequest {

    private Integer price;

    private String VINNumber;

    private String description;

    private String region;

    private Integer mileage;

    private Integer engineCapacity;

    private String engineType;

    private String transmissionType;

    private Vehicle vehicle;
}
