package dgb.Mp.user.Dtos;

import dgb.Mp.user.Role;
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

    private Long id;

    private String username;

    private String base64ContentImage;

    @Enumerated(EnumType.STRING)
    private Role role;



}
