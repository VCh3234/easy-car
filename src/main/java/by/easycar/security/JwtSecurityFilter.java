package by.easycar.security;

import by.easycar.security.service.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends GenericFilterBean {
    private final static String HEADER = "Authorization";
    private final JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    public JwtSecurityFilter(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = ((HttpServletRequest) servletRequest).getHeader(HEADER);
        String token;
        if(header != null) {
            token = header.substring(7);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            if (token != null && jwtAuthenticationService.isValidToken(token)) {
                Authentication authentication = jwtAuthenticationService.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(HttpStatus.UNAUTHORIZED.value());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
