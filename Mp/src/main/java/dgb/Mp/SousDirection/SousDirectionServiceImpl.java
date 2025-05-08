package dgb.Mp.SousDirection;
import dgb.Mp.generalAdvice.customException.SousDirectionNotFoundException;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.SousDirection.Dtos.SousDirectionDtoToAdd;
import dgb.Mp.SousDirection.Dtos.SousDirectionDto;
import dgb.Mp.user.User;
import dgb.Mp.user.UserService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@Setter


@Slf4j
@Service
@RequiredArgsConstructor
public class SousDirectionServiceImpl implements SousDirectionService{
    private final SousDirectionRepository sousDirectionRepository;
    private final Mapper mapper ;
    private final UserService userService;


    @Override
    public SousDirection getSousDirectionById(long id) {
            return sousDirectionRepository.findById(id).orElseThrow(()->new dgb.Mp.generalAdvice.customException.SousDirectionNotFoundException(id));
        }



    @Override
    public List<SousDirectionDto> getAllSousDirections() {
        return sousDirectionRepository.findAll().stream().map(mapper::toSousDirectionDto).collect(Collectors.toList());
    }



    @Override
    public SousDirectionDto addSousDirection(SousDirectionDtoToAdd sousDirectionDtoToAdd) {
        User director=userService.getUser(sousDirectionDtoToAdd.getDirectorUserId());
        SousDirection sousDirection= new SousDirection();
        sousDirection.setDirector(director);
        sousDirection.setSousDirectionName(sousDirectionDtoToAdd.getSousDirectionName());
        Set<User> employees = new HashSet<>();
        sousDirectionDtoToAdd.getEmployeesIds().forEach(employeesId -> {
            employees.add(userService.getUser(employeesId));
        });
        sousDirection.setEmployees(employees);

        return mapper.toSousDirectionDto(sousDirectionRepository.save(sousDirection));
    }



    @Override
    public SousDirectionDto updateSousDirection(SousDirectionDto  sousDirectionDto, Long id) {
        SousDirection existingSousDirection = sousDirectionRepository.findById(id).orElseThrow(() -> new SousDirectionNotFoundException(id));

        if (sousDirectionDto.getDirectorUserId() != null) {
            User director = userService.getUser(sousDirectionDto.getDirectorUserId());
            existingSousDirection.setDirector(director);
        }
        if (sousDirectionDto.getSousDirectionName() != null) {
            existingSousDirection.setSousDirectionName(sousDirectionDto.getSousDirectionName());
        }
        if (sousDirectionDto.getEmployeesIds() != null) {
            Set<User> employees = new HashSet<>();
            sousDirectionDto.getEmployeesIds().forEach(employeeId -> {employees.add(userService.getUser(employeeId));});
            existingSousDirection.setEmployees(employees);
        }

        SousDirection updatedSousDirection= sousDirectionRepository.save(existingSousDirection);

        return mapper.toSousDirectionDto(updatedSousDirection);
    }

    @Override
    public void deleteSousDirection(Long id) {
       SousDirection sousDirection= sousDirectionRepository.findById(id).orElseThrow(()->new SousDirectionNotFoundException(id));
        sousDirectionRepository.delete(sousDirection);

    }



}
