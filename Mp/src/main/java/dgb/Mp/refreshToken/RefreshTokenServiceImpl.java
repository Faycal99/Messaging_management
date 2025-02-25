package dgb.Mp.refreshToken;

import dgb.Mp.Utils.JwtUtils;
import dgb.Mp.generalAdvice.customException.RefreshTokenNotFoundException;
import dgb.Mp.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;




    @Override
    public RefreshToken getRefreshTokenbyToekn(String toekn) {
        return refreshTokenRepository.findByRefreshToken(toekn).orElseThrow(()-> new RefreshTokenNotFoundException("refresh token not found "));
    }

    @Override
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(JwtUtils.generateRefreshToken(user));
        refreshToken.setCreatedAt(JwtUtils.extractIssuedAt(refreshToken.getToken()));
        refreshToken.setExpiresAt(JwtUtils.extractIssuedAt(refreshToken.getToken()));
         return refreshTokenRepository.save(refreshToken);

    }

    @Override
    public RefreshToken deleteRefreshToken(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public boolean isRefreshTokenValid(RefreshToken refreshToken) {

        return new Date().before(refreshToken.getExpiresAt());
    }


}
