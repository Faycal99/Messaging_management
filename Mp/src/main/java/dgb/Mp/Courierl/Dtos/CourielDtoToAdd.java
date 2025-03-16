package dgb.Mp.Courierl.Dtos;

import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.Courierl.enums.Priority;
import dgb.Mp.Courierl.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourielDtoToAdd {

    private Integer courielNumber;
    private String subject;
    private Priority priority;
    private Couriel_Type type;
    private Status status;
    private Long archivedById;
    private Long fromDgbId;
    private Long fromDivisionId;
    private Long fromDirectionId;
    private Long fromSousDirectionId;
    private String fromExternal;
    private Long toDgbId;
    private Long toDivisionId;
    private Long toDirectionId;
    private Long toSousDirectionId;
    private String toExternal;
    private Date startDate; // For creationDate range
    private Date endDate;
}
