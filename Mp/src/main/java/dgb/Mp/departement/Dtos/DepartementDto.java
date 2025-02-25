package dgb.Mp.departement.Dtos;

import dgb.Mp.user.Dtos.UserDto;
import dgb.Mp.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartementDto {


    private Long id;

    private String departementName;

    private Long directorUserId;


    private Set<Long> employeesIds;
}
