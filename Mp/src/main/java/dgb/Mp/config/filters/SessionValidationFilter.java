package dgb.Mp.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Order(1)
public class SessionValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // Log the URI for debugging
        System.out.println("Filtering URI: " + requestURI);

        // Skip session validation for public endpoints
        if (requestURI.equals("/auth/login") || requestURI.equals("/auth/register") ||
                requestURI.startsWith("/swagger-ui/") || requestURI.startsWith("/v3/api-docs/")) {
            System.out.println("Skipping session validation for: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // Check if the user is authenticated via session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            System.out.println("Session valid for: " + requestURI);
            filterChain.doFilter(request, response);
        } else {
            System.out.println("No valid session for: " + requestURI);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in.");
        }
    }
}