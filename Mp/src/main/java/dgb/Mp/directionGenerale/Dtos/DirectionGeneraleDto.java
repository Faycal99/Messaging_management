package dgb.Mp.directionGenerale.Dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectionGeneraleDto {
    private Long id;
    private String directionGeneraleName;

    private Long directorUserId;


    private Set<Long> employeesIds;

}
