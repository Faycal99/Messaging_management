package dgb.Mp.Courierl;


import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.Courierl.enums.Priority;
import dgb.Mp.Courierl.enums.Status;
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

        private Integer Couriel_Number;

      //  private String sender;

        private String subject;

     //   private String description;

        @Enumerated(EnumType.STRING)
        private Priority priority;

        @Enumerated(EnumType.STRING)
        private Couriel_Type type;

        @Enumerated(EnumType.STRING)
        private Status status;

        @ManyToOne
        @JoinColumn(name = "archived_by_id")
        private User archivedBy;

        /*
       @ManyToOne
    @JoinColumn(name = "from_dgb_id", nullable = true)
    private Dgb fromDgb;

    @ManyToOne
    @JoinColumn(name = "from_division_id", nullable = true)
    private Division fromDivision;

      @ManyToOne
    @JoinColumn(name = "from_division_id", nullable = true)
    private Direction fromDirection;

    @ManyToOne
    @JoinColumn(name = "from_sous_direction_id", nullable = true)
    private SousDirection fromSousDirection;

    private Enum fromExternal; // e.g., "Ministry:Finance" for external sender

    // Receiver (To) relationships - only one should be set
    @ManyToOne
    @JoinColumn(name = "to_dgb_id", nullable = true)
    private Dgb toDgb;

    @ManyToOne
    @JoinColumn(name = "to_division_id", nullable = true)
    private Division toDivision;

       @ManyToOne
    @JoinColumn(name = "to_division_id", nullable = true)
    private Direction toDirection;

    @ManyToOne
    @JoinColumn(name = "to_sous_direction_id", nullable = true)
    private SousDirection toSousDirection;

    private EnumMinistry toExternal;
    */

        private Date creationDate;


}
