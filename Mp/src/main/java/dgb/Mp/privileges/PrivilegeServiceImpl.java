package dgb.Mp.privileges;

import dgb.Mp.generalAdvice.customException.PrivilegeNotFoundException;
import dgb.Mp.privileges.Dtos.PrivilegeDtoToAdd;
import dgb.Mp.privileges.enums.privilegeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private PrivilegeRepository privilegeRepository;


    @Override
    public Privilege getPrivilege(Long privilegeId) {
        return privilegeRepository.findById(privilegeId).orElseThrow(()-> new PrivilegeNotFoundException("Privilege Not Found with that given id "+privilegeId));
    }

    @Override
    public Set<Privilege> getAllPrivileges() {

        return (Set<Privilege>) privilegeRepository.findAll();
    }

    @Override
    public Privilege getPrivilegeByName(String privilegeName) {
        try {
            // Convert the string to the privilegeEnum
            privilegeEnum enumName = privilegeEnum.valueOf(privilegeName.toUpperCase());

            // Query the repository
            return privilegeRepository.findByName(enumName)
                    .orElseThrow(() -> new RuntimeException("Privilege not found: " + privilegeName));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid privilege name: " + privilegeName);
        }
    }
    @Override
    public Privilege addPrivilege(PrivilegeDtoToAdd privilegeDtoToAdd) {
        privilegeEnum privilegeEnu;

          try {
            privilegeEnu = privilegeEnum.valueOf(privilegeDtoToAdd.getName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid privilege name: " + privilegeDtoToAdd.getName());
        }
        Privilege privilege = privilegeRepository.findByName(privilegeEnu)
                .orElse(new Privilege());
        if (privilege.getId() == null) {

            privilege.setName(privilegeEnum.valueOf(privilegeDtoToAdd.getName()));



        }
return privilegeRepository.save(privilege);
    }
}
