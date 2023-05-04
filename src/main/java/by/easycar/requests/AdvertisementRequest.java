package by.easycar.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AdvertisementRequest {

    @NotNull(message = "Price must be not null.")
    @Positive(message = "Price must be more than 0.")
    private Integer price;

    @NotBlank(message = "VIN number must be not empty.")
    @Length(min = 17, max = 17, message = "Length of the VIN must be 17.")
    private String VinNumber;

    @NotBlank(message = "Description must be not empty.")
    @Length(max = 1200, message = "Length of the description must be less than 1200.")
    private String description;

    @NotBlank(message = "Region must be not empty.")
    @Length(max = 100, message = "Length of the region must be less than 100.")
    private String region;

    @NotNull(message = "Mileage must be not null.")
    @Positive(message = "Mileage must be more than 0.")
    private Integer mileage;

    @NotNull(message = "Engine capacity must be not null.")
    @Positive(message = "Engine capacity must be more than 0.")
    private Integer engineCapacity;

    @NotBlank(message = "Engine type must be not empty.")
    @Length(max = 30, message = "Length of the engine type must be less than 30.")
    private String engineType;

    @NotBlank(message = "Transmission type must be not empty.")
    @Length(max = 30, message = "Length of the transmission type must be less than 30.")
    private String transmissionType;

    @NotBlank(message = "Brand must be not empty.")
    @Length(max = 30, message = "Length of the brand must be less than 30.")
    private String brand;

    @NotBlank(message = "Model must be not empty.")
    @Length(max = 30, message = "Length of the model must be less than 30.")
    private String model;

    @NotBlank(message = "Generation type must be not empty.")
    @Length(max = 30, message = "Length of the generation must be less than 30.")
    private String generation;

    @NotBlank(message = "Body type must be not empty.")
    @Length(max = 30, message = "Length of the body type must be less than 30.")
    private String bodyType;

    @NotNull(message = "Car year must be not null.")
    @Size(min = 1900, max = 2100, message = "Car year must be validated")
    private Integer carYear;
}