package dgb.Mp.config;

import com.example.jwt.security.JwtAuthenticationFilter;
import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.user.CustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

   @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth)-> auth
                        //Departement Controller


                .requestMatchers(HttpMethod.GET, "api/admin/v1/departements").hasAuthority("ADMIN")


                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/forgot-password/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()  // All other requests need to be authenticated
                .and()
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class) // Add JWT filter
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)  // Customize authentication failure responses
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // Handle access denied errors (e.g., forbidden access)
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                }));

        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils,customUserDetailsService) , BasicAuthenticationFilter.class)
                .exceptionHandling(DefaultHttpSecurityExpressionHandler->authenticationEntryPoint(authenticationEntryPoint()));
       return null;
   }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);  // Use custom user details service
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}
