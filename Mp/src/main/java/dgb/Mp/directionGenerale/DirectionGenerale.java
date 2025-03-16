package dgb.Mp.directionGenerale;
import dgb.Mp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DirectionGenerale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "directionGenerale_seq_gen", sequenceName = "directionGenerale_seq", allocationSize = 1)
    private Long id;
    private String directionGeneraleName;

    @OneToOne
    @JoinColumn(name = "director_id")
    private User director;

    @OneToMany(mappedBy = "directionGenerale")
    private Set<User> employees;


}
