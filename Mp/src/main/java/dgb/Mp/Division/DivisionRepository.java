package dgb.Mp.Division;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {


    Optional<Division> findById(Long id);
    Optional<Division>  findByName(String name);


}
