package by.easycar.service.security.admin;

import by.easycar.model.administration.Admin;
import by.easycar.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin loadUserByUsername(String name) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("Can't find user with name: " + name));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(Admin.ROLE::name);
        admin.setAuthorityList(grantedAuthorities);
        return admin;
    }
}