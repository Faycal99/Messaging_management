package dgb.Mp.authService;

import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.generalAdvice.customException.InvalidRefreshTokenException;
import dgb.Mp.refreshToken.RefreshToken;
import dgb.Mp.refreshToken.RefreshTokenRepository;
import dgb.Mp.refreshToken.RefreshTokenService;
import dgb.Mp.user.Dtos.SuccessfulLoginDto;
import dgb.Mp.user.Dtos.UserDtoLogin;
import dgb.Mp.user.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AutheService {

    private final AuthenticationManager authenticationManager;


    private final RefreshTokenService refreshTokenService;

    private final Mapper mapper;
    private final RefreshTokenRepository refreshTokenRepository;


    private  Clock clock;


    @Override
    public SuccessfulLoginDto login(UserDtoLogin userDtoLogin) {

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userDtoLogin.getUsername(),
               userDtoLogin.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        SecurityUser userdetails = (SecurityUser) auth.getPrincipal();
        String jwtToken = JwtUtils.generateAccessToken(userdetails.getUser());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userdetails.getUser()) ;



        return returnSuccessfulLogin(jwtToken, refreshToken);
    }

    @Override
    public SuccessfulLoginDto refreshToken(String refreshToken) {
        RefreshToken refreshToken1 = refreshTokenService.getRefreshTokenbyToekn(refreshToken);

        if (!refreshTokenService.isRefreshTokenValid(refreshToken1)) {
            throw new InvalidRefreshTokenException("refreshToken is expired or invalid");
        }
        String token = JwtUtils.generateAccessToken(refreshToken1.getUser());
        refreshToken1.setToken(JwtUtils.generateRefreshToken(refreshToken1.getUser()));
        refreshToken1.setCreatedAt(Date.from(Instant.now(clock)));
        refreshToken1.setExpiresAt(Date.from(Instant.now(clock).plusMillis(JwtUtils.refreshTokenExpiration)));

        refreshTokenRepository.save(refreshToken1);

        return returnSuccessfulLogin(token, refreshToken1);
    }

    @Override
    public SuccessfulLoginDto returnSuccessfulLogin(String accessToken, RefreshToken refreshToken) {


        return SuccessfulLoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresAt(refreshToken.getExpiresAt())
                .issuedAt(refreshToken.getCreatedAt())
                .user(mapper.toUserDto(refreshToken.getUser()))
                .build();
    }


}
