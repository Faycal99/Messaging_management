package dgb.Mp.config.filters;


import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.user.CustomUserDetailService;
import dgb.Mp.user.SecurityUser;
import dgb.Mp.user.User;
import dgb.Mp.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private dgb.Mp.Utils.JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailService customUserDetailService;// Assuming you have a service to fetch user by username

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the JWT token from the request header
        String token = extractTokenFromRequest(request);

        if (token != null && jwtUtils.validateToken(token) && !jwtUtils.isTokenExpired(token) ) {
            String username = jwtUtils.extractUsername(token); // Extract the username from the token

            // Fetch the user from the database using the username
            User user = (User) customUserDetailService.loadUserByUsername(username);

            if (user != null) {
                // Create a SecurityUser (wrap the User entity in SecurityUser)
                SecurityUser securityUser = new SecurityUser(user);

                // Create authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response); // Continue with the filter chain
    }

    // Extract the JWT token from the Authorization header
    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix and return the token
        }
        return null;
    }
}
