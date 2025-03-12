package dgb.Mp.controllers;

import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.refreshToken.Dtos.RefreshTokenRequestDto;
import dgb.Mp.refreshToken.RefreshToken;
import dgb.Mp.refreshToken.RefreshTokenRepository;
import dgb.Mp.refreshToken.RefreshTokenService;
import dgb.Mp.user.Dtos.SuccessfulLoginDto;
import dgb.Mp.user.Dtos.UserDto;
import dgb.Mp.user.Dtos.UserDtoLogin;
import dgb.Mp.user.Dtos.UserDtoToAdd;
import dgb.Mp.user.SecurityUser;
import dgb.Mp.user.User;
import dgb.Mp.user.UserRepository;
import dgb.Mp.user.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserRepository userRepository;
@Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Mapper mapper;

@Autowired
    private RefreshTokenRepository refreshTokenRepository;

    // Login endpoint to authenticate user and generate tokens
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody UserDtoLogin loginRequest) {
        log.debug("Login attempt for username: {}", loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            log.debug("Authentication successful for: {}", loginRequest.getUsername());

            Object principal = authentication.getPrincipal();
            log.debug("Principal type: {}, value: {}", principal.getClass().getName(), principal);
            User user;
            if (principal instanceof User) {
                user = (User) principal;
            } else if (principal instanceof dgb.Mp.user.SecurityUser) {
                user = ((dgb.Mp.user.SecurityUser) principal).getUser();
            } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
                String username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
                user = userRepository.findUserByUserName(username)
                        .orElseThrow(() -> new RuntimeException("User not found: " + username));
            } else {
                throw new RuntimeException("Unexpected principal type: " + principal.getClass().getName());
            }
            log.debug("Fetched user entity: {}", user.getEmail());

            if (jwtUtils == null) {
                log.error("jwtUtils is not initialized");
                throw new RuntimeException("jwtUtils is not initialized");
            }
            log.debug("About to generate access token for user: {}", user.getEmail());
            String accessToken = jwtUtils.generateAccessToken(user);
            System.out.println("Access token: " + accessToken);
            log.debug("About to generate refresh token for user: {}", user.getEmail());
            String refreshToken = jwtUtils.generateRefreshToken(user);
            System.out.println("Refresh token: " + refreshToken);
            log.debug("Generated tokens - Access: {}, Refresh: {}", accessToken, refreshToken);

            RefreshToken token = new RefreshToken();
            token.setRefreshToken(refreshToken);
            token.setUser(user);
            token.setExpiresAt(jwtUtils.extractExpiration(refreshToken));
            log.debug("About to save refresh token for user: {}", user.getEmail());
            refreshTokenService.saveRefreshToken(token);
            log.debug("Refresh token saved for user: {}", user.getEmail());

            SuccessfulLoginDto response = new SuccessfulLoginDto(accessToken, refreshToken, mapper.toUserDto(user));
            log.debug("Returning successful login response for: {}", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error during login for {}: {}", loginRequest.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Refresh token endpoint (called to get new access token)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            // Validate the refresh token from the database
            RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh token not found"));

            // Check if the refresh token is expired
            if (storedToken.getExpiresAt().before(new Date())) {
                throw new RuntimeException("Refresh token has expired");
            }

            // Get the user from the stored token
            User user = userRepository.findById(storedToken.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Optionally, delete the refresh token after it is used
            refreshTokenRepository.delete(storedToken);  // Or mark it as used/inactive if needed

            // Generate a new access token for the user
            String newAccessToken = jwtUtils.generateAccessToken(user);

            return ResponseEntity.ok(newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDtoToAdd userDtoToAdd,
                            @AuthenticationPrincipal SecurityUser currentUser) {
        try {
            if (currentUser == null) {
                System.out.println("No authenticated user found!");
                throw new IllegalStateException("No authenticated user provided");
            }
            User entityUser = currentUser.getUser();
            //

             mapper.toUserDto( userService.createUser(userDtoToAdd, entityUser));
             return ResponseEntity.ok(mapper.toUserDto(entityUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Registration");
        }

}}
