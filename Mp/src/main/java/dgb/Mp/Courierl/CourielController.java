package dgb.Mp.Courierl;


import dgb.Mp.Courierl.Dtos.CourielDto;
import dgb.Mp.Courierl.Dtos.CourielDtoToAdd;
import dgb.Mp.Utils.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/Couriers")
@RequiredArgsConstructor
@Tag(name="Couriers api " , description = "Endpoints for managing Couriers")
public class CourielController {
    private final CourielService courielService;
    private final Mapper mapper;

    @GetMapping("/{id}")
    public CourielDto getCourierById(Long id) {
        return mapper.toCourielDto(courielService.getCourielById(id));
    }

    @GetMapping
    public List<CourielDto> getAllCouriers() {
        return courielService.getAllCouriels();
    }
    @PostMapping
    @Operation(summary = "Create new Couriel",description = "add new couriel")

    public ResponseEntity<CourielDto> createCouriel(@Valid @RequestBody CourielDtoToAdd courielDtoToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courielService.addCouriel(courielDtoToAdd));
    }

    @PutMapping("/{id}")
    public CourielDto updateCouriel(@PathVariable Long id, @RequestBody CourielDto courielDto) {
        return courielService.updateCouriel(courielDto, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCouriel(@PathVariable Long id) {
        courielService.deleteCouriel(id);
        return ResponseEntity.ok("Deleted Couriel :"+id);
    }

    @PostMapping("/filter")
    public List<CourielDto> filterCouriels(@RequestBody CourielDto courielDto) {
        List<CourielDto> filteredCouriels = courielService.filterCouriels(courielDto);
        return filteredCouriels;
    }
}
