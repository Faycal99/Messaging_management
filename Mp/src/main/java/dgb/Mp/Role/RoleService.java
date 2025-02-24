package dgb.Mp.Role;

import dgb.Mp.Role.Dtos.RoleDto;
import dgb.Mp.Role.Dtos.RoleDtoToAdd;
import dgb.Mp.Role.Dtos.RoleToUpdateDto;

public interface RoleService {

    Role getRole(Long id);
    Role getRoleByName(String name);

   /* Role AddRole(RoleDtoToAdd roleDto);*/

    Role UpdateRolePrivileges(RoleToUpdateDto roleToUpdateDto);

    Role DeleteRole(Long id);
}
