package by.easycar.config;

import by.easycar.filters.JwtSecurityFilter;
import by.easycar.handlers.SecurityExceptionsHandler;
import by.easycar.model.administration.Admin;
import by.easycar.model.user.UserPrivate;
import by.easycar.service.security.user.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    private final SecurityExceptionsHandler securityExceptionsHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtSecurityFilter jwtSecurityFilter, SecurityExceptionsHandler securityExceptionsHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtSecurityFilter = jwtSecurityFilter;
        this.securityExceptionsHandler = securityExceptionsHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        this.setAllMatchers(http);
        http.exceptionHandling().authenticationEntryPoint(securityExceptionsHandler);
        http.exceptionHandling().accessDeniedHandler(securityExceptionsHandler);
        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        http.userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtSecurityFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    private void setAllMatchers(HttpSecurity http) throws Exception {
        this.setMatchersForUserController(http);
        this.setMatchersForOperationalEndpoints(http);
        this.setMatchersForAuthenticationController(http);
        this.setMatchersForPaymentController(http);
        this.setMatchersForAdvertisementController(http);
        this.setMatchersForVerifyController(http);
        this.setMatchersForAdminController(http);
        this.setMatchersForImageController(http);
    }

    private void setMatchersForUserController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/users").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/users").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/users/update-password").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority(UserPrivate.ROLE.name());
    }

    private void setMatchersForOperationalEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui/index.html").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll();
    }

    private void setMatchersForAuthenticationController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/admin/login").permitAll();
    }

    private void setMatchersForPaymentController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/pays").hasAuthority(Admin.ROLE.name())
                .requestMatchers("/pays").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.POST, "/pays/get-token-for-demonstration").hasAuthority(Admin.ROLE.name());
    }

    private void setMatchersForAdvertisementController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/ads").permitAll()
                .requestMatchers("/ads/moderation").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.POST, "/ads/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/ads").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers("/ads/my-ads").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/ads/**").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/ads/up/**").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.DELETE, "/ads/**").hasAnyAuthority(UserPrivate.ROLE.name());
    }

    private void setMatchersForVerifyController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/verify/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/verify/**").hasAuthority(UserPrivate.ROLE.name());
    }

    private void setMatchersForAdminController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAuthority(Admin.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/admin/**").hasAuthority(Admin.ROLE.name())
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(Admin.ROLE.name())
                .requestMatchers(HttpMethod.DELETE, "/admin/**").hasAuthority(Admin.ROLE.name());
    }

    private void setMatchersForImageController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/images/**").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/images/**").hasAuthority(UserPrivate.ROLE.name())
                .requestMatchers(HttpMethod.DELETE, "/images/**").hasAnyAuthority(UserPrivate.ROLE.name());
    }
}