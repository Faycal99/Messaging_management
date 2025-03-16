package dgb.Mp.Division;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "division_seq_gen", sequenceName = "division_seq", allocationSize = 1)

    private Long id;
    private String name;

    /*
    @OneToMany()
    private Set<User> employees;
     */



    /*
    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL)
    private List<Direction> directions;
     */




}


