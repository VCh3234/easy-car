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
import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin loadUserByUsername(String name) throws UsernameNotFoundException {
        if (name.equals("SUPER_ADMIN")) {
            Optional<Admin> optional = adminRepository.findByName("SUPER_ADMIN");
            if (optional.isEmpty()) {
                Admin admin = new Admin();
                admin.setName("SUPER_ADMIN");
                admin.setPassword("$2a$12$68FNynNX7Q4Fo2rSa0ACTeabgdxJy6..gycXpOZoDXT0okckBWtYC");
                adminRepository.save(admin);
            }
        }
        Admin admin = adminRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("Can't find user with name: " + name));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(Admin.ROLE::name);
        admin.setAuthorityList(grantedAuthorities);
        return admin;
    }
}