package dgb.Mp.Role;

import dgb.Mp.Role.Dtos.RoleDto;
import dgb.Mp.Role.Dtos.RoleDtoToAdd;
import dgb.Mp.Role.Dtos.RoleToUpdateDto;
import dgb.Mp.generalAdvice.customException.EmptyPrivilegeListException;
import dgb.Mp.generalAdvice.customException.RoleNotFoundException;
import dgb.Mp.privileges.Privilege;
import dgb.Mp.privileges.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    private final PrivilegeService privilegeService;

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new RoleNotFoundException("Role Not Found with id: " + id));
    }

    @Override
    public Role getRoleByName(String name) {

        return roleRepository.findByName(name);
    }

  /*  @Override
    public Role AddRole(RoleDtoToAdd roleDto) {

        Role role = new Role();
        role.setName(roleDto.getName());

        Set<Privilege> privileges = roleDto.getPrivilegesIdsList().stream().map(privilegeService::getPrivilegeByName).collect(Collectors.toSet());

        role.setPrivileges(privileges);

       return roleRepository.save(role);

    }*/

    @Override
    public Role UpdateRolePrivileges(RoleToUpdateDto roleToUpdateDto) {

        Role role = getRole(roleToUpdateDto.getId());

        if(roleToUpdateDto.getPrivilegesIdsList().size()>0) {
            throw new EmptyPrivilegeListException("Privilege list is empty add at least one privilege to that roel to update it !");
        }

        Set<Privilege> privileges = roleToUpdateDto.getPrivilegesIdsList().stream().map(privilegeService::getPrivilegeByName).collect(Collectors.toSet());

        role.setPrivileges(privileges);
        return roleRepository.save(role);

    }

    @Override
    public Role DeleteRole(Long id) {
        Role role = getRole(id);
        return roleRepository.delete(role);
    }

}
