package dgb.Mp.user.Dtos;

import dgb.Mp.Role.Role;
import dgb.Mp.Role.enums.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoToAdd {



    private String email;

    @Enumerated(EnumType.STRING)
    private RoleName role;



}
