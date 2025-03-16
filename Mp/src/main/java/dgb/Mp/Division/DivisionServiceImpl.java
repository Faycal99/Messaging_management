package dgb.Mp.Division;


import dgb.Mp.Division.Dto.DivisionDto;
import dgb.Mp.Division.Dto.DivisionDtoToAdd;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.generalAdvice.customException.DivisionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {


    private final DivisionRepository divisionRepository;
    private final Mapper mapper;




    @Override
    public DivisionDto getDivisionById(Long id) {
        return divisionRepository.findById(id).map(mapper::toDivisionDto).orElseThrow(() -> new DivisionNotFoundException(id));
    }


    @Override
    public List<DivisionDto> getAllDivision() {
        return divisionRepository.findAll().stream().map(mapper::toDivisionDto).toList();
    }


    @Override
    public DivisionDto addDivision(DivisionDtoToAdd divisionDtoToAdd) {

        Division division = new Division();
        division.setName(divisionDtoToAdd.getName());


        /*
        Set<User> employees = new HashSet<>();
        divisionDtoToAdd.getEmployeesIds().forEach(employeesId -> {
            employees.add(userService.getUser(employeesId));
        });
        division.setEmployees(employees);
         */

        return mapper.toDivisionDto(divisionRepository.save(division));
    }



    @Override
    public DivisionDto updateDivision(DivisionDto divisionDto, Long id) {

        Division division = divisionRepository.findById(id).orElseThrow(()->new DivisionNotFoundException(id));
        division.setName(divisionDto.getName());

        return mapper.toDivisionDto(divisionRepository.save(division));
    }



    @Override
    public void deleteDivision(Long id) {

        divisionRepository.deleteById(id);
        Division division = divisionRepository.findById(id).orElseThrow(()->new DivisionNotFoundException(id));
        divisionRepository.delete(division);

    }



}
