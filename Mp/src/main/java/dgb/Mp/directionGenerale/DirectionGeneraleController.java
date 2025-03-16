package dgb.Mp.directionGenerale;

import dgb.Mp.Utils.Mapper;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDto;
import dgb.Mp.directionGenerale.Dtos.DirectionGeneraleDtoToAdd;
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
@RequestMapping("/DirectionGenerales")
@RequiredArgsConstructor
@Tag(name="DirectionGenerale api " , description = "Endpoints for managing DirectionGenerale")
public class DirectionGeneraleController {
    private final DirectionGeneraleService directionGeneraleService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public DirectionGeneraleDto getDirectionGenerale(@PathVariable long id){
        return mapper.toDirectionGeneraleDto(directionGeneraleService.getDirectionGeneraleById(id));
    }

    @GetMapping
    public List<DirectionGeneraleDto> getAllDirectionGenerales(){ return directionGeneraleService.getAllDirectionGenerales(); }
    @PostMapping
    @Operation(summary = "Create new directionGenerale",description = "add new directionGenerale")
    public ResponseEntity<DirectionGeneraleDto> createDirectionGenerale(@RequestBody DirectionGeneraleDtoToAdd directionGeneraleDtoToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(directionGeneraleService.addDirectionGenerale(directionGeneraleDtoToAdd));
    }

    @PutMapping("/{id}")
    public DirectionGeneraleDto updateDirectionGenerale(@PathVariable Long id, @RequestBody DirectionGeneraleDto directionGeneraleDto) {
        return directionGeneraleService.updateDirectionGenerale(directionGeneraleDto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirectionGenerale(@PathVariable Long id) {
        directionGeneraleService.deleteDirectionGenerale(id);
        return ResponseEntity.ok("directionGenerale deleted with id: " + id);
    }

}
