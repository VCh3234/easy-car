package by.easycar.config;


import by.easycar.controllers.handlers.SecurityExceptionsHandler;
import by.easycar.filters.JwtSecurityFilter;
import by.easycar.model.administration.Admin;
import by.easycar.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtSecurityFilter jwtSecurityFilter;

    private final SecurityExceptionsHandler securityExceptionsHandler;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtSecurityFilter jwtSecurityFilter, SecurityExceptionsHandler securityExceptionsHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtSecurityFilter = jwtSecurityFilter;
        this.securityExceptionsHandler = securityExceptionsHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/pay/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()

                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/user/update").hasAuthority("USER")
                .requestMatchers(HttpMethod.DELETE, "/user").hasAuthority("USER")

                .requestMatchers(HttpMethod.POST, "/user/**").hasRole(Admin.ROLE.name())
                .requestMatchers(HttpMethod.PUT, "/user/**").hasRole(Admin.ROLE.name())
                .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole(Admin.ROLE.name());

        http.httpBasic();

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
}
