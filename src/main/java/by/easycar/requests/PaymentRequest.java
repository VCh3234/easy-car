package by.easycar.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message = "User id must be not null.")
    @NotBlank(message = "User id must be not empty.")
    private Long userId;

    @NotNull(message = "Bank name must be not null.")
    @NotBlank(message = "Bank name must be not empty.")
    private String bankName;

    @NotNull(message = "Operation name must be not null.")
    @NotBlank(message = "Operation name must be not empty.")
    private String operationName;

    @NotNull(message = "Transaction number must be not null.")
    @NotBlank(message = "Transaction number must be not empty.")
    private String transactionNumber;
}