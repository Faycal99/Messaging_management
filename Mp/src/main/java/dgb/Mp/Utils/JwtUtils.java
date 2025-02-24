package dgb.Mp.Utils;

import dgb.Mp.privileges.Privilege;
import dgb.Mp.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class JwtUtils {

    @Value("${jwt.secret}")
    private static String secretKey;

    @Value("${jwt.expiration.access}")
    private static long accessTokenExpiration;

    @Value("${jwt.expiration.refresh}")
    private static long refreshTokenExpiration;



    // Generate access token
    public static String generateAccessToken(User user) {
        // Collect the role and privileges
        String role = user.getRole().getName();  // Role (e.g., "ADMIN")
        List<String> privileges = user.getRole().getPrivileges().stream()
                .map(privilege -> privilege.getName().toString()) // Privileges (e.g., "READ_PRIVILEGE")
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration)) // Access token valid for 1 hour
                .claim("role", role) // Store role name in the "role" claim
                .claim("privileges", privileges) // Store privileges list in the "privileges" claim
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    // Generate refresh token

    // Helper method to generate JWT token including subject (user) and authorities (roles)
    public static String generateRefreshToken(User user) {
        // Collect the role and privileges
        String role = user.getRole().getName();  // Role (e.g., "ADMIN")
        List<String> privileges = user.getRole().getPrivileges().stream()
                .map(privilege -> privilege.getName().toString()) // Privileges (e.g., "READ_PRIVILEGE")
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // Refresh token valid for 15 days
                .claim("role", role) // Store role name in the "role" claim
                .claim("privileges", privileges) // Store privileges list in the "privileges" claim
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate token
    public static boolean validateToken(String token) {
        try {
            // Parsing the token and validating signature, expiration, etc.
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract claims from the token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract username from token
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract authorities (roles) from token
    public static String extractAuthorities(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Get expiration date
    public static Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public static boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date()); // Return true if the token has expired
    }
}

