package by.easycar.service.security.admin;

import by.easycar.exceptions.security.WrongPasswordException;
import by.easycar.model.administration.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminSecurityService {

    private final AdminDetailsService adminDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSecurityService(AdminDetailsService adminDetailsService, PasswordEncoder passwordEncoder) {
        this.adminDetailsService = adminDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String email, String password) {
        Admin admin = adminDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, admin.getPassword())) {
            return true;
        } else {
            throw new WrongPasswordException("Wrong name or password.");
        }
    }
}