package by.easycar.service.security.admin;

import by.easycar.exceptions.security.JwtAuthenticationException;
import by.easycar.model.administration.Admin;
import by.easycar.service.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class AdminJwtAuthenticationService implements JwtService {

    private final AdminDetailsService adminDetailsService;

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expire.time.minutes}")
    private long TIME_OF_EXPIRATION;

    @Autowired
    public AdminJwtAuthenticationService(AdminDetailsService adminDetailsService) {
        this.adminDetailsService = adminDetailsService;
    }

    @PostConstruct
    private void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    @Override
    public String getToken(String name) {
        Date currentDate = new Date();
        return Jwts.builder().setSubject(name).setIssuedAt(currentDate).setExpiration(new Date(currentDate.getTime() + (TIME_OF_EXPIRATION * 60_000))).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is invalid.");
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Admin admin = adminDetailsService.loadUserByUsername(getStringFromToken(token));
        return new UsernamePasswordAuthenticationToken(admin, "", admin.getAuthorities());
    }

    private String getStringFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}