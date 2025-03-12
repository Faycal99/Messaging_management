package dgb.Mp.departement;

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
public class Departement {

    // this entity created by demand of ait hadad to make all
    // departement in the ministry can create there own system here
    // like DGB space for couriel management and DGI space for there couriel management

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "depart_seq_gen", sequenceName = "depart_seq", allocationSize = 1)
    private Long id;

    private String departementName;

    @OneToOne
    @JoinColumn(name = "director_id")
    private User director;

    @OneToMany()
    private Set<User> employees;



}
