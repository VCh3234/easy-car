package by.easycar.service.security.user;

import by.easycar.exceptions.security.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSecurityService(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String email, String password) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return true;
        } else {
            throw new WrongPasswordException("Wrong email or password.");
        }
    }
}