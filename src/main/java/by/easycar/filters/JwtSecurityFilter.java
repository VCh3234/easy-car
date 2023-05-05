package by.easycar.filters;

import by.easycar.exceptions.security.JwtAuthenticationException;
import by.easycar.service.security.JwtService;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import by.easycar.service.security.user.UserJwtAuthenticationService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtSecurityFilter implements Filter {

    private final static String HEADER = "Authorization";

    private final UserJwtAuthenticationService userJwtAuthenticationService;

    private final AdminJwtAuthenticationService adminJwtAuthenticationService;

    @Autowired
    public JwtSecurityFilter(UserJwtAuthenticationService userJwtAuthenticationService, AdminJwtAuthenticationService adminJwtAuthenticationService) {
        this.userJwtAuthenticationService = userJwtAuthenticationService;
        this.adminJwtAuthenticationService = adminJwtAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = ((HttpServletRequest) servletRequest).getHeader(HEADER);
        String token;
        if (header != null) {
            token = header.substring(7);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            if (((HttpServletRequest) servletRequest).getRequestURI().contains("/admin/")) {
                authenticate(adminJwtAuthenticationService, token);
            } else {
                authenticate(userJwtAuthenticationService, token);
            }
        } catch (AuthenticationException e) {
            ((HttpServletResponse) servletResponse).setStatus(401);
            PrintWriter writer = servletResponse.getWriter();
            writer.println(e.getMessage());
            writer.flush();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void authenticate(JwtService jwtService, String token) throws AuthenticationException {
        if (jwtService.isValidToken(token)) {
            Authentication authentication = jwtService.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return;
            }
        }
        throw new JwtAuthenticationException("Unauthorized");
    }
}