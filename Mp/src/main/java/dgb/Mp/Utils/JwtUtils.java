package dgb.Mp.Utils;

import dgb.Mp.privileges.Privilege;
import dgb.Mp.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.access}")
    private long accessTokenExpiration;

    @Value("${jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    // Generate access token
    public String generateAccessToken(User user) {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret key is not configured");
        }
        String role = user.getRole().getName().name();
        List<String> privileges = user.getRole().getPrivileges().stream()
                .map(privilege -> privilege.getName().toString())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .claim("role", role)
                .claim("privileges", privileges)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(User user) {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret key is not configured");
        }
        String role = user.getRole().getName().name();
        List<String> privileges = user.getRole().getPrivileges().stream()
                .map(privilege -> privilege.getName().toString())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .claim("role", role)
                .claim("privileges", privileges)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract claims from the token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract authorities (roles) from token
    public String extractAuthorities(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Get expiration date
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}

