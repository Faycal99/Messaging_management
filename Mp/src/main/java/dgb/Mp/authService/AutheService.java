package dgb.Mp.authService;

import dgb.Mp.refreshToken.RefreshToken;
import dgb.Mp.user.Dtos.SuccessfulLoginDto;
import dgb.Mp.user.Dtos.UserDtoLogin;

public interface AutheService {

    SuccessfulLoginDto login(UserDtoLogin userDtoLogin);

    SuccessfulLoginDto refreshToken(String refreshToken);

    SuccessfulLoginDto returnSuccessfulLogin(String accessToken, RefreshToken refreshToken);
}
