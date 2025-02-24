package dgb.Mp.user.Dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulLoginDto {


        private String accessToken;

        private String refreshToken;

        private UserDto user;
        // getters and setters
    }





