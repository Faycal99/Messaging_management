package dgb.Mp.user.Dtos;

import dgb.Mp.Picrures.Picture;
import dgb.Mp.Role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String userName;


    private Role role;


    private String password;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;
}
