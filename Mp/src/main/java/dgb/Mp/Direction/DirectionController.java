package dgb.Mp.Direction;
import dgb.Mp.SousDirection.Dtos.SousDirectionDto;
import dgb.Mp.SousDirection.Dtos.SousDirectionDtoToAdd;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.Direction.Dtos.DirectionDto;
import dgb.Mp.Direction.Dtos.DirectionDtoToAdd;
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
@RequestMapping("/sousDirections")
@RequiredArgsConstructor
@Tag(name="Direction api " , description = "Endpoints for managing Directions")

public class DirectionController {
    private final DirectionService directionService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public DirectionDto getDirectionById(@PathVariable Long id) {
        return mapper.toDirectionDto(directionService.getDirectionById(id));

    }


    // Récupérer toutes les sous-directions
    @GetMapping
    public List<DirectionDto> getAllDirections(){ return directionService.getAllDirections();}
    @PostMapping
    @Operation(summary = "Create new Direction",description = "Add new Direction")
    public ResponseEntity<DirectionDto> create(@RequestBody DirectionDtoToAdd directionDtoToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directionService.addDirection(directionDtoToAdd));
    }



    //  Mettre à jour une sous-direction
    @PutMapping("/{id}")
    public DirectionDto updateSousDirection(@PathVariable Long id, @RequestBody DirectionDto directionDto) {
        return directionService.updateDirection(directionDto, id);
    }


    // Supprimer une sous-direction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
        return ResponseEntity.ok(" Direction deleted with id: " + id);
    }








}
