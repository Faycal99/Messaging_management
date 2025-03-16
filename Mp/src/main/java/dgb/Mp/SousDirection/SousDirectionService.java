package dgb.Mp.SousDirection;

import dgb.Mp.SousDirection.Dtos.SousDirectionDto;
import dgb.Mp.SousDirection.Dtos.SousDirectionDtoToAdd;

import java.util.List;

public interface SousDirectionService {



    public SousDirection getSousDirectionById(long id);
    public List<SousDirectionDto> getAllSousDirections();
    public SousDirectionDto  updateSousDirection (SousDirectionDto sousDirectionDto,Long id);
    public SousDirectionDto addSousDirection (SousDirectionDtoToAdd sousDirectionDtoToAdd);
    public void deleteSousDirection(Long id);
}
