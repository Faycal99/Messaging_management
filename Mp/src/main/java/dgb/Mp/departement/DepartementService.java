package dgb.Mp.departement;

import dgb.Mp.departement.Dtos.DepartementDto;
import dgb.Mp.departement.Dtos.DepartementDtoToAdd;

import java.util.List;

public interface DepartementService {

    public Departement getDepartementById(Long id);

    public List<DepartementDto> getAllDepartement();

    public DepartementDto addDepartement(DepartementDtoToAdd departementDtoToAdd);

    public DepartementDto updateDepartement(DepartementDto departementDto, Long id);

    public void deleteDepartement(Long id);


}
