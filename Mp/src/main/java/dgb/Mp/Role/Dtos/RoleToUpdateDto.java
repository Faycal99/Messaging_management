package dgb.Mp.Role.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleToUpdateDto {

    private Long Id;

    private String Name;

    private Set<String> privilegesIdsList;

}
