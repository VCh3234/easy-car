package by.easycar.service.mappers;

import by.easycar.model.Payment;
import by.easycar.model.requests.PaymentRequest;
import by.easycar.model.user.UserPrivate;

public class PaymentMapper {
    public static Payment getPaymentFromPaymentRequest(PaymentRequest paymentRequest, UserPrivate user) {
        Payment payment = new Payment();
        payment.setOperationName(paymentRequest.getOperationName());
        payment.setBankName(paymentRequest.getBankName());
        payment.setUser(user);
        payment.setTransactionNumber(paymentRequest.getTransactionNumber());
        return payment;
    }
}
