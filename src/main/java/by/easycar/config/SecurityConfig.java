package by.easycar.config;


import by.easycar.controllers.handlers.SecurityExceptionsHandler;
import by.easycar.filters.JwtSecurityFilter;
import by.easycar.service.security.UserDetailsServiceImpl;
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
        http.formLogin().failureHandler(securityExceptionsHandler);

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
    }

    private void setMatchersForUserController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/user").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/user/update").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/user/update-password").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("USER");
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
                .requestMatchers("/auth/admin/login").permitAll()
                .requestMatchers("/auth/logout").permitAll();
    }

    private void setMatchersForPaymentController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/pay/**").permitAll()
                .requestMatchers( "/pay/**").permitAll();
    }

    private void setMatchersForAdvertisementController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/ad/public").permitAll()
                .requestMatchers("/ad").permitAll()
                .requestMatchers("/ad/of-user").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT, "/ad/update/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/ad/create").hasAuthority("USER")
                .requestMatchers(HttpMethod.DELETE, "/ad/delete/**").hasAnyAuthority("USER");
    }

    private void setMatchersForVerifyController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/verify/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.PUT,"/verify/**").hasAuthority("USER");
    }

    private void setMatchersForAdminController(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/admin/**").hasAuthority("ADMIN");
    }
}
