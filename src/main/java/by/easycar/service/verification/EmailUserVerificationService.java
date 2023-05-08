package by.easycar.service.verification;


import by.easycar.model.user.UserPrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("verify_email")
public class EmailUserVerificationService implements VerificationService {

    @Value("${url.of.server}")
    private String URL;

    @Value("${server.port}")
    private String PORT;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailUserVerificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(UserPrivate userPrivate, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("UladCherap@yandex.by");
        mailMessage.setTo(userPrivate.getEmail());
        mailMessage.setSubject("Activating code.");
        mailMessage.setText(String.format("Hello, follow to the link for verify your account in Easy car project. \n" +
                "http://%s:%s/verify/%s", URL, PORT, code));
        mailSender.send(mailMessage);
    }
}