package dgb.Mp.user.Dtos;

import dgb.Mp.Picrures.Picture;
import dgb.Mp.Role.Role;
import jakarta.persistence.*;

public class UserDto {

    private Long id;

    private String userName;


    private Role role;


    private String password;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;
}
