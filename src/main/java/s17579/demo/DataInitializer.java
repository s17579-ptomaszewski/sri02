package s17579.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Car;
import s17579.demo.sri02.repo.BranchRepository;
import s17579.demo.sri02.repo.CarRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationRunner {

    private CarRepository carRepository;
    private BranchRepository branchRepository;

    public DataInitializer(CarRepository carRepository, BranchRepository branchRepository){
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Car c1 = Car.builder()
                .modelName("Tesla")
                .color("black")
                .createDate(LocalDate.now())
                .doorNumber(5)
                .build();
        Car c2 = Car.builder()
                .modelName("Peugot")
                .color("black")
                .createDate(LocalDate.now())
                .doorNumber(3)
                .build();
        Car c3 = Car.builder()
                .modelName("KIA")
                .color("black")
                .createDate(LocalDate.now())
                .doorNumber(3)
                .build();
        Car c4 = Car.builder()
                .modelName("Volvo")
                .color("black")
                .createDate(LocalDate.now())
                .doorNumber(3)
                .build();
        Branch b1 = Branch.builder()
                .branchName("Warszawa")
                .cars(new HashSet<>())
                .build();
        Branch b2 = Branch.builder()
                .branchName("Krakow")
                .cars(new HashSet<>())
                .build();
        Branch b3 = Branch.builder()
                .branchName("Lublin")
                .cars(new HashSet<>())
                .build();

        c1.setOwnerBranch(b1);
        b1.getCars().add(c1);

        c2.setOwnerBranch(b2);
        b2.getCars().add(c2);

        branchRepository.saveAll(Arrays.asList(b1, b2, b3));
        carRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
    }
}
