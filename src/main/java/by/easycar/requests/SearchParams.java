package by.easycar.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchParams {

    @NotNull(message = "Entity must be not null.")
    @NotBlank(message = "Entity number must be not empty.")
    private String entity;

    @NotNull(message = "Key must be not null.")
    @NotBlank(message = "Key must be not empty.")
    private String key;

    @NotNull(message = "Operation must be not null.")
    @NotBlank(message = "Operation must be not empty.")
    private String operation;

    @NotNull(message = "Value must be not null.")
    @NotBlank(message = "Value must be not empty.")
    private String value;
}