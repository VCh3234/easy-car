package by.easycar.service.verification;

import by.easycar.model.user.UserPrivate;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("verify_sms")
public class SmsUserVerificationService implements VerificationService {

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String TWILIO_PHONE_NUMBER;

    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber("+375" + userPrivate.getPhoneNumber()),
                new PhoneNumber(TWILIO_PHONE_NUMBER), "Your code for verify: " + code).create();
    }
}