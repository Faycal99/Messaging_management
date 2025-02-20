package dgb.Mp.Courierl;


import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Couriel {


        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @SequenceGenerator(name = "couriel_seq_gen", sequenceName = "couriel_seq", allocationSize = 1)
        private Long id;

        private String sender;

        private String subject;

        private String description;

        @Enumerated(EnumType.STRING)
        private Couriel_Type type;

        @ManyToOne
        private User createdBy;

        private Date creationDate;


}
