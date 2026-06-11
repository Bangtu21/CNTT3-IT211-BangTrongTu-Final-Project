package spring_boot.it211projectfinal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring_boot.it211projectfinal.model.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes());
    }

    public String generateToken(
            User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim(
                        "role",
                        user.getRole().name())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(
            String token) {

        return extractClaims(token)
                .getSubject();
    }

    public String extractRole(
            String token) {

        return extractClaims(token)
                .get(
                        "role",
                        String.class);
    }

    public boolean validateToken(
            String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Claims extractClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(
            String token){

        return extractClaims(token)
                .getExpiration();
    }
}
