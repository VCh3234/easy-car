package by.easycar.security;

import by.easycar.model.user.UserPrivate;
import by.easycar.repository.UserRepository;
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

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPrivate userPrivate = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist with email: " + email));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(() -> "USER");
        grantedAuthorities.add(() -> "id_" + userPrivate.getId());
        UserSecurity user =  new UserSecurity(userPrivate.getId(), userPrivate.getPassword(), userPrivate.getEmail(), grantedAuthorities);
        return user;
    }
}
