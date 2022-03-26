package s17579.demo.sri02.model.car;

import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();

}
