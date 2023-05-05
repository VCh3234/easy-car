package by.easycar.service.security.user;

import by.easycar.model.user.UserPrincipal;
import by.easycar.model.user.UserPrivate;
import by.easycar.repository.UserRepository;
import by.easycar.service.security.admin.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final AdminDetailsService adminDetailsService;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, AdminDetailsService adminDetailsService) {
        this.userRepository = userRepository;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        try {
            UserPrivate userPrivate = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist with username: " + username));
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(UserPrivate.ROLE::name);
            userDetails = new UserPrincipal(userPrivate.getId(), userPrivate.getPassword(), userPrivate.getEmail(), grantedAuthorities);
        } catch (UsernameNotFoundException e) {
            userDetails = adminDetailsService.loadUserByUsername(username);
        }
        return userDetails;
    }
}