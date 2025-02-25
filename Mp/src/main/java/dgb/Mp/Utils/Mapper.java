package dgb.Mp.Utils;

import dgb.Mp.Picrures.Dtos.PictureDto;
import dgb.Mp.Picrures.Picture;
import dgb.Mp.Role.Dtos.RoleDto;
import dgb.Mp.Role.Role;
import dgb.Mp.privileges.Privilege;
import dgb.Mp.privileges.PrivilegeDto.PrivilegeDto;
import dgb.Mp.privileges.PrivilegeDto.PrivilegesDtos;
import dgb.Mp.user.Dtos.UserDto;
import dgb.Mp.user.User;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;



@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {


    PrivilegeDto toPrivilegeDto(Privilege privilege);

    PictureDto toPictureDto(Picture picture);

    @Mapping(source = "privileges", target = "privilegesIdsList")
    RoleDto toRoleDto(Role role);


    UserDto toUserDto(User user);


    static Set<String> toSetOfString(Set<Privilege> privileges){

        if (privileges == null) {

            return null;
        }
        return privileges.stream()
                .map(Privilege->(Privilege.getName().toString()))
                .collect(Collectors.toSet());


    }


}
