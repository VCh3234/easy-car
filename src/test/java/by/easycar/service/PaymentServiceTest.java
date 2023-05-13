package by.easycar.service;

import by.easycar.model.Payment;
import by.easycar.model.dto.PaymentRequest;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.PaymentRepository;
import by.easycar.service.mappers.PaymentMapper;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private Validator validator;

    private PaymentRequest paymentRequest;

    private PaymentService paymentService;

    private UserPrivate userPrivate;

    private Payment payment;

    private Map<String, String> paymentMap = new HashMap<>();

    private List<Payment> paymentList = new ArrayList<>();
    @BeforeEach
    public void init() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(1L);
        paymentRequest.setTransactionNumber("test");
        paymentRequest.setOperationName("10_ups");
        paymentRequest.setBankName("test");
        paymentService = new PaymentService(this.userService, this.paymentRepository, this.paymentMapper, this.validator);
        userPrivate = new UserPrivate();
        userPrivate.setId(1L);
        userPrivate.setUps(10);
        payment = new Payment();
        payment.setUser(userPrivate);
        payment.setBankName("test");
        payment.setOperationName("10_ups");
        payment.setPayTime(LocalDateTime.now());
        payment.setTransactionNumber("test");
        payment.setId(1L);
        paymentMap.put("bankName", "test");
        paymentMap.put("userId", "1");
        paymentMap.put("operationName", "10_ups");
        paymentMap.put("transactionNumber", "test");
        paymentList.add(payment);
    }

    @Test
    public void makePayTest() {
        when(userService.getById(1L)).thenReturn(userPrivate);
        when(paymentMapper.getPaymentFromPaymentRequest(paymentRequest,userPrivate)).thenReturn(payment);
        paymentService.makePay(paymentRequest);
        assertEquals(20, userPrivate.getUps());
        verify(paymentRepository).save(payment);
    }

    @Test
    public void getByIdTest() {
        when(validator.validate(paymentRequest)).thenReturn(new HashSet<>());
        when(userService.getById(1L)).thenReturn(userPrivate);
        when(paymentMapper.getPaymentFromPaymentRequest(paymentRequest,userPrivate)).thenReturn(payment);
        String token = paymentService.getToken(paymentMap);
        paymentService.verifyAndMakePay(token);
        verify(paymentRepository).save(payment);
    }

    @Test
    public void getPaymentsOfUserTest() {
        UserPrivate userPrivate = new UserPrivate();
        userPrivate.setId(1L);
        when(paymentRepository.findByUser(userPrivate)).thenReturn(paymentList);
        List<Payment> result = paymentService.getPaymentsOfUser(userPrivate.getId());
        assertEquals(result, paymentList);
    }
}