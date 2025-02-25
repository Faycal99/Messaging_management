package dgb.Mp.departement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartementDtoToAdd {

    private String departementName;

    private Long directorUserId;


    private Set<Long> employeesIds;
}
