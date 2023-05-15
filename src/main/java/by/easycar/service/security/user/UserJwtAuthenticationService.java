package by.easycar.service.security.user;

import by.easycar.exceptions.security.JwtAuthenticationException;
import by.easycar.service.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class UserJwtAuthenticationService implements JwtService {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expire.time.minutes}")
    private long TIME_OF_EXPIRATION;

    @Autowired
    public UserJwtAuthenticationService(@Qualifier("userDetailsServiceImpl") UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostConstruct
    private void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    @Override
    public String getToken(String email) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + (TIME_OF_EXPIRATION * 60_000)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
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

    private String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}