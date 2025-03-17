package dgb.Mp.directionGenerale;
import dgb.Mp.generalAdvice.customException.DepartementNotFoundException;

import dgb.Mp.Utils.Mapper;
import dgb.Mp.departement.Departement;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDto;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDtoToAdd;
import dgb.Mp.generalAdvice.customException.DirectionGeneraleNotFoundException;
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
public class DirectionGeneraleServiceImpl implements DirectionGeneraleService {

     private final DirectionGeneraleRepository directionGeneraleRepository;
     private final Mapper mapper;
    private final UserService userService;
    @Override
    public DirectionGenerale getDirectionGeneraleById(Long id) {
        return directionGeneraleRepository.findById(id).orElseThrow(()->new DirectionGeneraleNotFoundException(id));
    }

    @Override
    public List<DirectionGeneraleDto> getAllDirectionGenerales() {
        return directionGeneraleRepository.findAll().stream().map(mapper::toDirectionGeneraleDto).collect(Collectors.toList());

    }

    @Override
    public DirectionGeneraleDto addDirectionGenerale(DirectionGeneraleDtoToAdd directionGeneraleDtoToAdd) {
       User director =userService.getUser(directionGeneraleDtoToAdd.getDirectorUserId());
       DirectionGenerale directionGenerale=new DirectionGenerale();
       directionGenerale.setDirector(director);
       directionGenerale.setDirectionGeneraleName(directionGeneraleDtoToAdd.getDirectionGeneraleName());
       Set<User> employees =new HashSet<>();
       directionGeneraleDtoToAdd.getEmployeesIds().forEach(employeesId -> {
            employees.add(userService.getUser(employeesId));
        });
       directionGenerale.setEmployees(employees);
       return mapper.toDirectionGeneraleDto(directionGeneraleRepository.save(directionGenerale));

    }

    @Override
    public DirectionGeneraleDto updateDirectionGenerale(DirectionGeneraleDto directionGeneraleDto, Long id) {
        DirectionGenerale existingDirectionGenerale = directionGeneraleRepository.findById(id).orElseThrow(() -> new DirectionGeneraleNotFoundException(id));

        if (directionGeneraleDto.getDirectorUserId() != null) {
            User director = userService.getUser(directionGeneraleDto.getDirectorUserId());
            existingDirectionGenerale.setDirector(director);
        }
        if (directionGeneraleDto.getDirectionGeneraleName() != null) {
            existingDirectionGenerale.setDirectionGeneraleName(directionGeneraleDto.getDirectionGeneraleName());
        }
        if (directionGeneraleDto.getEmployeesIds() != null) {
            Set<User> employees = new HashSet<>();
            directionGeneraleDto.getEmployeesIds().forEach(employeeId -> {employees.add(userService.getUser(employeeId));});
            existingDirectionGenerale.setEmployees(employees);
        }

        DirectionGenerale updatedDirectionGenerale = directionGeneraleRepository.save(existingDirectionGenerale);

        return mapper.toDirectionGeneraleDto(updatedDirectionGenerale);
    }

    @Override
    public void deleteDirectionGenerale(Long id) {
     DirectionGenerale directionGenerale = directionGeneraleRepository.findById(id).orElseThrow(() -> new DirectionGeneraleNotFoundException(id));
     directionGeneraleRepository.delete(directionGenerale);
    }
}
