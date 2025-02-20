package dgb.Mp.user.Dtos;

import dgb.Mp.user.Role;
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
    private Role role;



}
