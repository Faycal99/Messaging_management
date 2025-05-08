package dgb.Mp.Courierl;

import dgb.Mp.Courierl.Dtos.CourielDto;
import dgb.Mp.Courierl.Dtos.CourielDtoToAdd;
import dgb.Mp.Division.Division;
import dgb.Mp.Division.DivisionService;
import dgb.Mp.Utils.Mapper;
import dgb.Mp.directionGenerale.DirectionGenerale;
import dgb.Mp.directionGenerale.DirectionGeneraleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourielServiceImpl implements CourielService{
    @Autowired
    private Mapper mapper;
    @Autowired
    private EntityManager entityManager;

    private final DirectionGeneraleService directionGeneraleService;
    private final DivisionService divisionService;

    private CourielRepository courielRepository;
    @Override
    public List<CourielDto> filterCouriels(CourielDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Couriel> cq = cb.createQuery(Couriel.class);
        Root<Couriel> root = cq.from(Couriel.class);
        List<Predicate> predicates = new ArrayList<>();

        // Apply filters dynamically
        if (filter.getId() != null) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if (filter.getCourielNumber() != null) {
            predicates.add(cb.equal(root.get("courielNumber"), filter.getCourielNumber()));
        }
        if (filter.getSubject() != null && !filter.getSubject().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("subject")), "%" + filter.getSubject().toLowerCase() + "%"));
        }
        if (filter.getPriority() != null) {
            predicates.add(cb.equal(root.get("priority"), filter.getPriority()));
        }
        if (filter.getType() != null) {
            predicates.add(cb.equal(root.get("type"), filter.getType()));
        }
        if (filter.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getStatus()));
        }
        if (filter.getArchivedById() != null) {
            predicates.add(cb.equal(root.get("archivedBy").get("id"), filter.getArchivedById()));
        }
        if (filter.getFromDirectionGeneralId() != null) {
            predicates.add(cb.equal(root.get("fromDgb").get("id"), filter.getFromDirectionGeneralId()));
        }
        if (filter.getFromDivisionId() != null) {
            predicates.add(cb.equal(root.get("fromDivision").get("id"), filter.getFromDivisionId()));
        }
        if (filter.getFromSousDirectionId() != null) {
            predicates.add(cb.equal(root.get("fromSousDirection").get("id"), filter.getFromSousDirectionId()));
        }
        if (filter.getFromExternal() != null) {
            predicates.add(cb.equal(root.get("fromExternal"), filter.getFromExternal()));
        }
        if (filter.getToDirectionGeneralId() != null) {
            predicates.add(cb.equal(root.get("toDgb").get("id"), filter.getToDirectionGeneralId()));
        }
        if (filter.getToDivisionId() != null) {
            predicates.add(cb.equal(root.get("toDivision").get("id"), filter.getToDivisionId()));
        }
        if (filter.getToSousDirectionId() != null) {
            predicates.add(cb.equal(root.get("toSousDirection").get("id"), filter.getToSousDirectionId()));
        }
        if (filter.getToExternal() != null  ) {
            predicates.add(cb.equal(root.get("toExternal"), filter.getToExternal()));
        }
        if (filter.getArrivedDate() != null && filter.getReturnDate() != null) {
            predicates.add(cb.between(root.get("creationDate"), filter.getArrivedDate(), filter.getReturnDate()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<Couriel> results = entityManager.createQuery(cq).getResultList();
        return results.stream()
                .map(mapper::toCourielDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourielDto addCouriel(CourielDtoToAdd courielDtoToAdd) {
        Couriel couriel= new Couriel();
        couriel.setCouriel_Number(courielDtoToAdd.getCourielNumber());
        couriel.setSubject(courielDtoToAdd.getSubject());
        couriel.setType(courielDtoToAdd.getType());
        couriel.setPriority(courielDtoToAdd.getPriority());
        couriel.setStatus(courielDtoToAdd.getStatus());
        couriel.setArrivedDate(courielDtoToAdd.getArrivedDate());
        couriel.setRegisterDate(courielDtoToAdd.getRegisterDate());
        couriel.setReturnDate(courielDtoToAdd.getReturnDate());

        DirectionGenerale todirectionGenerale = directionGeneraleService.getDirectionGeneraleById(courielDtoToAdd.getToDirectionId());
        DirectionGenerale fromdirectionGenerale = directionGeneraleService.getDirectionGeneraleById(courielDtoToAdd.getFromDirectionId());
        Division todivision = divisionService.getDivisionById(courielDtoToAdd.getToDivisionId());
        Division fromdivision = divisionService.getDivisionById(courielDtoToAdd.getFromDivisionId());

        couriel.setToDirectionGenerale(todirectionGenerale);
        couriel.setFromDirectionGenerale(fromdirectionGenerale);
        couriel.setToDivision(todivision);
        couriel.setFromDivision(todivision);

        return mapper.toCourielDto(courielRepository.save(couriel));

    }

    @Override
    public CourielDto updateCouriel(CourielDto courielDto,Long id) {
        // Fetch the existing Couriel by ID, throw exception if not found
        Couriel couriel = courielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couriel not found with id: " + id));

        // Update fields from DTO
        couriel.setCouriel_Number(courielDto.getCourielNumber());
        couriel.setSubject(courielDto.getSubject());
        couriel.setType(courielDto.getType());
        couriel.setPriority(courielDto.getPriority());
        couriel.setStatus(courielDto.getStatus());
        couriel.setArrivedDate(courielDto.getArrivedDate());
        couriel.setRegisterDate(courielDto.getRegisterDate());
        couriel.setReturnDate(courielDto.getReturnDate());

        // Fetch and update related entities (if IDs changed)
        if (courielDto.getToDirectionId() != null) {
            DirectionGenerale toDirectionGenerale = directionGeneraleService.getDirectionGeneraleById(courielDto.getToDirectionId());
            couriel.setToDirectionGenerale(toDirectionGenerale); // Assuming a setter exists
        }
        if (courielDto.getFromDirectionId() != null) {
            DirectionGenerale fromDirectionGenerale = directionGeneraleService.getDirectionGeneraleById(courielDto.getFromDirectionId());
            couriel.setFromDirectionGenerale(fromDirectionGenerale);
        }
        if (courielDto.getToDivisionId() != null) {
            Division toDivision = divisionService.getDivisionById(courielDto.getToDivisionId());
            couriel.setToDivision(toDivision);
        }
        if (courielDto.getFromDivisionId() != null) {
            Division fromDivision = divisionService.getDivisionById(courielDto.getFromDivisionId());
            couriel.setFromDivision(fromDivision);
        }

        // Save updated entity and return DTO
        return mapper.toCourielDto(courielRepository.save(couriel));
    }

    @Override
    public void deleteCouriel(Long id) {

        Couriel couriel = courielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couriel not found with id: " + id));
        courielRepository.delete(couriel);

    }

    @Override
    public Couriel getCourielById(Long id) {
        return courielRepository.findById(id).orElseThrow(() -> new RuntimeException("Couriel not found with id: " + id));
    }

    @Override
    public List<CourielDto> getAllCouriels() {
        return courielRepository.findAll().stream().map(mapper::toCourielDto).collect(Collectors.toList());
    }


}
