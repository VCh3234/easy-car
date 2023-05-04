package by.easycar.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchParams {

    @NotBlank(message = "Entity number must be not empty.")
    private String entity;

    @NotBlank(message = "Key must be not empty.")
    private String key;

    @NotBlank(message = "Operation must be not empty.")
    private String operation;

    @NotBlank(message = "Value must be not empty.")
    private String value;
}