package dgb.Mp.privileges;

import java.util.Set;

public interface PrivilegeService {

    Privilege getPrivilege(Long privilegeId);

    Set<Privilege> getAllPrivileges();

    Privilege getPrivilegeByName(String privilegeName);
}
