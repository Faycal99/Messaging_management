package dgb.Mp.controllers;

import dgb.Mp.Role.Dtos.RoleDto;
import dgb.Mp.Role.Dtos.RoleDtoToAdd;
import dgb.Mp.Role.RoleService;
import dgb.Mp.Utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController("/cm/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final Mapper mapper;


    @PostMapping
    public RoleDto addRole(@RequestBody RoleDtoToAdd roleDto) {
       return mapper.toRoleDto( roleService.AddRole(roleDto));


    }
}
