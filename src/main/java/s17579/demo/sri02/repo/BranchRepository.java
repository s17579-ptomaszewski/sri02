package s17579.demo.sri02.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Car;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends CrudRepository<Branch, Long> {
    List<Branch> findAll();

    @Query("from Branch as c left join fetch c.cars where c.id=:branchId")
    Optional<Branch> getBranchDetailsById(@Param("branchId") Long branchId);

    @Query("select c.cars from Branch as c where c.id = :branchId")
    List<Car> findCarByBranchId(@PathVariable Long branchId);
}
