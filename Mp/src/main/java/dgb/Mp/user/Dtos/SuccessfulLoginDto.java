package dgb.Mp.user.Dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessfulLoginDto {


        private String accessToken;

        private String refreshToken;

        private UserDto user;

        private Date expiresAt;

        private Date issuedAt;
        // getters and setters
    }





