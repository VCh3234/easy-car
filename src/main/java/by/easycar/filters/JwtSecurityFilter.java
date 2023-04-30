package by.easycar.filters;

import by.easycar.service.security.JwtAuthenticationService;
import by.easycar.service.security.JwtService;
import by.easycar.service.security.admin.AdminJwtAuthenticationService;
import jakarta.servlet.*;
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
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AdminJwtAuthenticationService adminJwtAuthenticationService;

    @Autowired
    public JwtSecurityFilter(JwtAuthenticationService jwtAuthenticationService, AdminJwtAuthenticationService adminJwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
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
        boolean result;
        if(((HttpServletRequest) servletRequest).getRequestURI().contains("/admin/")) {
            result = authenticate(adminJwtAuthenticationService, token, servletResponse);
        } else {
            result = authenticate(jwtAuthenticationService, token, servletResponse);
        }
        if(result) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean authenticate(JwtService jwtService,String token, ServletResponse servletResponse) throws IOException {
        try {
            if (jwtService.isValidToken(token)) {
                Authentication authentication = jwtService.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                }
            }
            return false;
        } catch (AuthenticationException e) {
            ((HttpServletResponse) servletResponse).setStatus(401);
            PrintWriter writer = servletResponse.getWriter();
            writer.println(e.getMessage());
            writer.flush();
            return false;
        }
    }
}
