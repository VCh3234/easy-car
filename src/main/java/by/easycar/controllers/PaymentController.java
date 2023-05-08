package by.easycar.controllers;

import by.easycar.model.Payment;
import by.easycar.model.user.UserPrincipal;
import by.easycar.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pays")
@Tag(name = "Payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Deposit ups on account", security = {@SecurityRequirement(name = "Admin JWT")})
    @PostMapping
    private ResponseEntity<String> deposit(@RequestBody String jwt) {
        paymentService.verifyAndMakePay(jwt);
        return new ResponseEntity<>("Payment was created", HttpStatus.OK);
    }

    @Operation(summary = "Get payments of user", security = {@SecurityRequirement(name = "User JWT")})
    @GetMapping
    private ResponseEntity<List<Payment>> getPaymentsOfUser(@AuthenticationPrincipal @Parameter(hidden = true) UserPrincipal userPrincipal) {
        List<Payment> payments = paymentService.getPaymentsOfUser(userPrincipal.getId());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Operation(summary = "Get token for deposit", security = {@SecurityRequirement(name = "Admin JWT")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = {@ExampleObject(name = "User 1 10 ups", value = "{\n" +
                    "    \"userId\":1,\n" +
                    "    \"bankName\":\"BANKNAME\",\n" +
                    "    \"operationName\":\"10_ups\",\n" +
                    "    \"transactionNumber\":\"TRANSACTION\"\n" +
                    "}")
            })))
    @PostMapping("/get-token-for-demonstration")
    private ResponseEntity<String> getToken(@RequestBody Map<String, String> paymentRequest) {
        String token = paymentService.getToken(paymentRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}