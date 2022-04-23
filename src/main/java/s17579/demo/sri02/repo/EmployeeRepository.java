package s17579.demo.sri02.repo;

import org.springframework.data.repository.CrudRepository;
import s17579.demo.sri02.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAll();

}
