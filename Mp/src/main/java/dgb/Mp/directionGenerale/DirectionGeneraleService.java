package dgb.Mp.directionGenerale;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDto;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDtoToAdd;

import java.util.List;
public interface DirectionGeneraleService {
    public DirectionGenerale getDirectionGeneraleById(Long id);

    public List<DirectionGeneraleDto> getAllDirectionGenerales();

    public DirectionGeneraleDto addDirectionGenerale(DirectionGeneraleDtoToAdd directionGeneraleDtoToAdd);

    public DirectionGeneraleDto updateDirectionGenerale(DirectionGeneraleDto directionGeneraleDto, long id );

    public void deleteDirectionGenerale(long id);
}

