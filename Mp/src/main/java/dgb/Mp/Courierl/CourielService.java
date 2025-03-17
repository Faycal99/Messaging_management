package dgb.Mp.Courierl;

import dgb.Mp.Courierl.Dtos.CourielDto;
import dgb.Mp.Courierl.Dtos.CourielDtoToAdd;
import dgb.Mp.Courierl.enums.Couriel_Type;
import dgb.Mp.Courierl.enums.Priority;
import dgb.Mp.Courierl.enums.Status;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public interface CourielService {
    List<CourielDto> filterCouriels(CourielDto courielDto);

      public CourielDto addCouriel(CourielDtoToAdd courielDtoToAdd);

    public CourielDto updateCouriel(CourielDto courielDto, Long id );

    public void deleteCouriel(Long id);

    public Couriel getCourielById(Long id);

    public List<CourielDto> getAllCouriels();




}
