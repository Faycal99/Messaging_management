package dgb.Mp.controllers;

import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.refreshToken.RefreshToken;
import dgb.Mp.refreshToken.RefreshTokenRepository;
import dgb.Mp.refreshToken.RefreshTokenService;
import dgb.Mp.user.Dtos.SuccessfulLoginDto;
import dgb.Mp.user.Dtos.UserDtoLogin;
import dgb.Mp.user.User;
import dgb.Mp.user.UserRepository;
import dgb.Mp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Mapper mapper;

    // Login endpoint to authenticate user and generate tokens
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDtoLogin loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // If authentication is successful, generate access token and refresh token
            User user = (User) authentication.getPrincipal();  // Assuming User is fetched here
            String accessToken = jwtUtils.generateAccessToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(user);  // Generate the refresh token

            // Store the refresh token in the database
            RefreshToken token = new RefreshToken();
            token.setToken(refreshToken);
            token.setUser(user);
            token.setExpiresAt(jwtUtils.extractExpiration(refreshToken));
            refreshTokenService.saveRefreshToken(token);

            // Return both tokens to the client
            return ResponseEntity.ok(new SuccessfulLoginDto(accessToken,refreshToken, mapper.toUserDto(user)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Refresh token endpoint (called to get new access token)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            // Validate the refresh token from the database
            RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh token not found"));

            // Check if the refresh token is expired
            if (storedToken.getExpirationDate().before(new Date())) {
                throw new RuntimeException("Refresh token has expired");
            }

            // Get the user from the stored token
            User user = userRepository.findById(storedToken.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Optionally, delete the refresh token after it is used
            refreshTokenRepository.delete(storedToken);  // Or mark it as used/inactive if needed

            // Generate a new access token for the user
            String newAccessToken = jwtUtils.generateAccessToken(user);

            return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }





}
