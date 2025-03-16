package dgb.Mp.Courierl;

import dgb.Mp.Courierl.Dtos.CourielDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourielServiceImpl implements CourielService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EntityManager entityManager;
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
        if (filter.getCouriel_Number() != null) {
            predicates.add(cb.equal(root.get("courielNumber"), filter.getCouriel_Number()));
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
        if (filter.getArchivedByUserId() != null) {
            predicates.add(cb.equal(root.get("archivedBy").get("id"), filter.getArchivedByUserId()));
        }
        if (filter.getFromDgbId() != null) {
            predicates.add(cb.equal(root.get("fromDgb").get("id"), filter.getFromDgbId()));
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
        if (filter.getToDgbId() != null) {
            predicates.add(cb.equal(root.get("toDgb").get("id"), filter.getToDgbId()));
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
        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            predicates.add(cb.between(root.get("creationDate"), filter.getStartDate(), filter.getEndDate()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<Couriel> results = entityManager.createQuery(cq).getResultList();
        return results.stream()
                .map(c -> modelMapper.map(c, CourielDto.class))
                .collect(Collectors.toList());
    }
}
