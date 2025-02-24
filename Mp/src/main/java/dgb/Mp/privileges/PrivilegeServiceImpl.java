package dgb.Mp.privileges;

import dgb.Mp.generalAdvice.customException.PrivilegeNotFoundException;
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
}
