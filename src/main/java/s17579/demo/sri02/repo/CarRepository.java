package s17579.demo.sri02.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Car;


import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
    @Query("select c.ownerBranch from Car as c where c.id = :carId")
   Branch findBranchByCarId(@PathVariable Long carId);
}
