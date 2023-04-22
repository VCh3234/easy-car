package by.easycar.security;


import by.easycar.model.administration.Admin;
import by.easycar.model.user.UserPrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtSecurityFilter jwtSecurityFilter;
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtSecurityFilter jwtSecurityFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtSecurityFilter = jwtSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/user/register").permitAll()

                .requestMatchers("/user/**").hasRole(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.POST, "/user/**").hasRole(Admin.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/user/**").hasRole(Admin.ROLE.name())
                .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole(Admin.ROLE.name());
        http.httpBasic();
        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        http.userDetailsService(userDetailsService);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
