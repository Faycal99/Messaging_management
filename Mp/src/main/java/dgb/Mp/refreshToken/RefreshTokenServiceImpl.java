package dgb.Mp.refreshToken;

import dgb.Mp.generalAdvice.customException.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
