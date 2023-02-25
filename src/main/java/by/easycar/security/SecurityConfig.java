package by.easycar.security;

import by.easycar.repository.model.user.Permission;
import by.easycar.repository.model.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/user/**").hasAuthority(Permission.USERS_READ.getPermission()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(Permission.USERS_WRITE.getPermission()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority(Permission.USERS_WRITE.getPermission()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE, "/api/user").hasAuthority(Permission.USERS_WRITE.getPermission()));
        http.authorizeHttpRequests().requestMatchers("/**").permitAll();
//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); //work correctly
        http.httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() { //TODO
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(Role.ROLE_ADMIN.getAuthorities())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .authorities(Role.ROLE_USER.getAuthorities())
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
