package dgb.Mp.user;

import dgb.Mp.Picrures.Picture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;



}
