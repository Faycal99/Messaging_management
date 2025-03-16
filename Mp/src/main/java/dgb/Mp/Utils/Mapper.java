package dgb.Mp.Utils;

import dgb.Mp.Division.Division;
import dgb.Mp.Division.Dto.DivisionDto;
import dgb.Mp.Picrures.Dtos.PictureDto;
import dgb.Mp.Picrures.Picture;
import dgb.Mp.Role.Dtos.RoleDto;
import dgb.Mp.Role.Role;
import dgb.Mp.SousDirection.Dtos.SousDirectionDto;
import dgb.Mp.SousDirection.SousDirection;
import dgb.Mp.departement.Departement;
import dgb.Mp.departement.Dtos.DepartementDto;
import dgb.Mp.privileges.Dtos.PrivilegeDto;
import dgb.Mp.directionGenerale.DirectionGenerale;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDto;
import dgb.Mp.privileges.Privilege;
import dgb.Mp.user.Dtos.UserDto;
import dgb.Mp.user.User;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;



@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

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
//
    DepartementDto toDepartementDto(Departement departement);
    PrivilegeDto toPrivilegeDto(Privilege privilege);
    DivisionDto toDivisionDto(Division division);




    DirectionGeneraleDto toDirectionGeneraleDto(DirectionGenerale directionGenerale);
    SousDirectionDto toSousDirectionDto(SousDirection sousDirection);
}
