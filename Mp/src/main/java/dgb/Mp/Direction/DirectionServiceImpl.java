package dgb.Mp.Direction;
import dgb.Mp.SousDirection.SousDirection;
import dgb.Mp.generalAdvice.customException.DirectionNotFoundException;
import dgb.Mp.generalAdvice.customException.SousDirectionNotFoundException;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.Direction.Dtos.DirectionDto;
import dgb.Mp.Direction.Dtos.DirectionDtoToAdd;
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

public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;
    private final Mapper mapper;
    private final UserService userService;


    @Override
    public Direction getDirectionById(long id ){
        return directionRepository.findById(id).orElseThrow(()->new dgb.Mp.generalAdvice.customException.DirectionNotFoundException(id));
    }

    @Override
    public List<DirectionDto> getAllDirections() {
        return directionRepository.findAll().stream().map(mapper::toDirectionDto).collect(Collectors.toList());
    }

    @Override
    public DirectionDto updateDirection(DirectionDto directionDto, Long id) {
        Direction existingDirection = directionRepository.findById(id).orElseThrow(() -> new DirectionNotFoundException(id));

        if (directionDto.getDirectorUserId() != null) {
            User director = userService.getUser(directionDto.getDirectorUserId());
            existingDirection.setDirector(director);
        }
        if (directionDto.getDirectionName() != null) {
            existingDirection.setDirectionName(directionDto.getDirectionName());
        }
        if (directionDto.getEmployeesIds() != null) {
            Set<User> employees = new HashSet<>();
            directionDto.getEmployeesIds().forEach(employeeId -> {employees.add(userService.getUser(employeeId));});
            existingDirection.setEmployees(employees);
        }

        Direction updatedDirection= directionRepository.save(existingDirection);

        return mapper.toDirectionDto(updatedDirection);
    }

    @Override
    public DirectionDto addDirection(DirectionDtoToAdd directionDtoToAdd) {
        User director=userService.getUser(directionDtoToAdd.getDirectorUserId());
        Direction direction= new Direction();
        direction.setDirector(director);
        direction.setDirectionName(directionDtoToAdd.getDirectionName());
        Set<User> employees = new HashSet<>();
        directionDtoToAdd.getEmployeesIds().forEach(employeesId -> {
            employees.add(userService.getUser(employeesId));
        });
        direction.setEmployees(employees);

        return mapper.toDirectionDto(directionRepository.save(direction));


    }

    @Override
    public void deleteDirection(Long id) {
        Direction direction= directionRepository.findById(id).orElseThrow(()->new DirectionNotFoundException(id));
       directionRepository.delete(direction);

    }

}
