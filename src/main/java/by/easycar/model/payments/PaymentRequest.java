package by.easycar.model.payments;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private String bankName;
    private String operationName;
    private String transactionNumber;
}
