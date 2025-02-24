package dgb.Mp.refreshToken;


import dgb.Mp.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "refToken_seq_gen", sequenceName = "refToken_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    private  User user;

    private  String token;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date  createdAt;

    private Date  expiresAt;


}
