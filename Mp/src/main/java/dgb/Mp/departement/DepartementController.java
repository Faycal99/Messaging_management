package dgb.Mp.departement;


import dgb.Mp.Utils.Mapper;
import dgb.Mp.departement.Dtos.DepartementDto;
import dgb.Mp.departement.Dtos.DepartementDtoToAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Departements")
@RequiredArgsConstructor
@Tag(name="Departement api " , description = "Endpoints for managing Departements")
public class DepartementController {
    private final DepartementService departementService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public DepartementDto getDepartement(@PathVariable Long id) {
        return mapper.toDepartementDto(departementService.getDepartementById(id));

    }
    @GetMapping
    public List<DepartementDto> getAllDepartements() {
        return departementService.getAllDepartement();
    }
    @PostMapping
    @Operation(summary = "Create new departement",description = "Add new departement")
    public ResponseEntity<DepartementDto> createDepartement(@RequestBody DepartementDtoToAdd departementDtoToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departementService.addDepartement(departementDtoToAdd));
    }

    @PutMapping("/{id}")
    public DepartementDto updateDepartement(@PathVariable Long id, @RequestBody DepartementDto departementDto) {
        return departementService.updateDepartement(departementDto, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Departement deleted with id: " + id);
    }
}
