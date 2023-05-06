package by.easycar.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message = "User id must be not null.")
    private Long userId;

    @NotBlank(message = "Bank name must be not empty.")
    private String bankName;

    @NotBlank(message = "Operation name must be not empty.")
    private String operationName;

    @NotBlank(message = "Transaction number must be not empty.")
    private String transactionNumber;
}