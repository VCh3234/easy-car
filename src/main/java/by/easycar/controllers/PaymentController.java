package by.easycar.controllers;

import by.easycar.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("")
    private ResponseEntity<String> deposit(@RequestBody String jwt) {
        paymentService.doPay(jwt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //For demonstration
    @PostMapping("/token")
    private ResponseEntity<String> getToken(@RequestBody Map<String, String> paymentRequest) {
        String token = paymentService.getToken(paymentRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
