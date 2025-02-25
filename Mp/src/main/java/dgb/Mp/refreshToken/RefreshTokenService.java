package dgb.Mp.refreshToken;

import dgb.Mp.user.User;

public interface RefreshTokenService {

    RefreshToken getRefreshTokenbyToekn(String toekn);

    RefreshToken saveRefreshToken(RefreshToken refreshToken);

    RefreshToken createRefreshToken(User user);

    RefreshToken deleteRefreshToken(RefreshToken refreshToken);

    boolean isRefreshTokenValid(RefreshToken refreshToken);


}
