package dgb.Mp.Courierl.Dtos;

import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.Courierl.enums.Priority;
import dgb.Mp.Courierl.enums.Status;
import dgb.Mp.validation.ValidCouriel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidCouriel(message = "Check your couriel fields!")
public class CourielDtoToAdd {

    private Integer courielNumber;
    private String subject;
    private Priority priority;
    private Couriel_Type type;
    private Status status;
    private Long archivedById;
    private Long fromDirectionGeneralId;
    private Long fromDivisionId;
    private Long fromDirectionId;
    private Long fromSousDirectionId;
    private String fromExternal;
    private Long toDirectionGeneralId;
    private Long toDivisionId;
    private Long toDirectionId;
    private Long toSousDirectionId;
    private String toExternal;
    private Date arrivedDate;
    private Date departureDate;
    private Date returnDate;
    private Date registerDate;
}
