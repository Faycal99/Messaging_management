package dgb.Mp.Courierl.Dtos;

import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.Courierl.enums.Priority;
import dgb.Mp.Courierl.enums.Status;
import dgb.Mp.Utils.AlgerianMinistries;
import dgb.Mp.user.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourielDto {
    private Long id;


    private Integer Couriel_Number;

    //  private String sender;

    private String subject;

    //   private String description;


    private Priority priority;
    private Status status;
    private Couriel_Type couriel_Type;


    private Couriel_Type type;


    private Long archivedByUserId;


    private Long fromDgbId;


    private Long fromDivisionId;


    private Long fromSousDirectionId;

  private AlgerianMinistries fromExternal; // e.g., "Ministry:Finance" for external sender

    // Receiver (To) relationships - only one should be set

    private Long toDgbId;


    private Long toDivisionId;

    private Long toSousDirectionId;

    private AlgerianMinistries toExternal;

    private Date startDate; // For creationDate range
    private Date endDate;

}
