package dgb.Mp.departement;

import dgb.Mp.Advices.CustomException.DepartementNotFoundException;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.departement.Dtos.DepartementDto;
import dgb.Mp.departement.Dtos.DepartementDtoToAdd;
import dgb.Mp.user.User;
import dgb.Mp.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements DepartementService{

    private final DepartementRepository departementRepository;
    private final Mapper mapper;
    private final UserService userService;

    @Override
    public Departement getDepartementById(Long id) {
        return departementRepository.findById(id).orElseThrow(()->new DepartementNotFoundException(id));
    }

    @Override
    public List<DepartementDto> getAllDepartement() {
        return departementRepository.findAll().stream().map(mapper::toDepartementDto).collect(Collectors.toList());
    }

    @Override
    public DepartementDto addDepartement(DepartementDtoToAdd departementDtoToAdd) {
        User director=userService.getUser(departementDtoToAdd.getDirectorUserId());
        Departement departement = new Departement();
        departement.setDirector(director);
        departement.setDepartementName(departementDtoToAdd.getDepartementName());
        Set<User> employees = new HashSet<>();
        departementDtoToAdd.getEmployeesIds().forEach(employeesId -> {
            employees.add(userService.getUser(employeesId));
        });
        departement.setEmployees(employees);

        return mapper.toDepartementDto(departementRepository.save(departement));
    }

    @Override
    public DepartementDto updateDepartement(DepartementDto departementDto, Long id) {
        Departement existingDepartement = departementRepository.findById(id).orElseThrow(() -> new DepartementNotFoundException(id));

        if (departementDto.getDirectorUserId() != null) {
            User director = userService.getUser(departementDto.getDirectorUserId());
            existingDepartement.setDirector(director);
        }
        if (departementDto.getDepartementName() != null) {
            existingDepartement.setDepartementName(departementDto.getDepartementName());
        }
        if (departementDto.getEmployeesIds() != null) {
            Set<User> employees = new HashSet<>();
            departementDto.getEmployeesIds().forEach(employeeId -> {employees.add(userService.getUser(employeeId));});
            existingDepartement.setEmployees(employees);
        }

        Departement updatedDepartement = departementRepository.save(existingDepartement);

        return mapper.toDepartementDto(updatedDepartement);
    }

    @Override
    public void deleteDepartement(Long id) {
        Departement departement = departementRepository.findById(id).orElseThrow(()->new DepartementNotFoundException(id));
        departementRepository.delete(departement);

    }
}
