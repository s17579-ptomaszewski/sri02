package s17579.demo.sri02.rest;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import s17579.demo.sri02.dto.BranchDetailsDto;
import s17579.demo.sri02.dto.BranchDto;
import s17579.demo.sri02.dto.CarDto;
import s17579.demo.sri02.mapper.BranchDtoMapper;
import s17579.demo.sri02.mapper.CarDtoMapper;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Car;
import s17579.demo.sri02.repo.BranchRepository;
import s17579.demo.sri02.repo.CarRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/branches")
public class BranchController {
    private CarRepository carRepository;

    private BranchRepository branchRepository;

    private BranchDtoMapper branchDtoMapper;

    private CarDtoMapper carDtoMapper;

    public BranchController(BranchRepository branchRepository, CarDtoMapper carDtoMapper,
                            BranchDtoMapper branchDtoMapper, CarRepository carRepository){
        this.carRepository = carRepository;
        this.branchRepository = branchRepository;
        this.carDtoMapper = carDtoMapper;
        this.branchDtoMapper = branchDtoMapper;
    }

    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<BranchDto>> getBranches() {
        List<Branch> allBranchEntities = branchRepository.findAll();
        List<BranchDto> result = allBranchEntities.stream()
                .map(branchDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(BranchDto dto : result){
            dto.add(createBranchSelfLink(dto.getId()));
            dto.add(createBranchCarsLink(dto.getId()));
        }
        Link linkSelf = linkTo(methodOn(BranchController.class).getBranches()).withSelfRel();
        CollectionModel<BranchDto> res = CollectionModel.of(result, linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchDetailsDto>
    getBranchById(@PathVariable Long branchId) {
        Optional<Branch> branch =
                branchRepository.findById(branchId);
        if(branch.isPresent()) {
            BranchDetailsDto branchDetailsDto = branchDtoMapper.convertToDetailsDto(branch.get());
            Link linkSelf = createBranchSelfLink(branchId);
            branchDetailsDto.add(linkSelf);
            return new ResponseEntity<>(branchDetailsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("list/{branchId}")
    public List<CarDto> getCarsListByBranchId(@PathVariable Long branchId) {
        List<Car> carList = branchRepository.findCarByBranchId(branchId);
        List<CarDto> result = carList.stream()
                .map(carDtoMapper::convertToDto)
                .collect(Collectors.toList());
        if(!result.isEmpty()) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping
    public ResponseEntity saveNewBranch(@RequestBody BranchDto branch) {
        Branch entity = branchDtoMapper.convertToEntity(branch);
        branchRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{branchId}/add/{carId}")
    public ResponseEntity addCarToBranch(@PathVariable Long branchId, @PathVariable Long carId) {
        Optional<Car> currentCar = carRepository.findById(carId);
        Optional<Branch> currentBranch = branchRepository.findById(branchId);
        if(currentCar.isPresent() && currentBranch.isPresent()) {

           Car car = currentCar.get();
           car.setOwnerBranch(currentBranch.get());

           Branch branch =  currentBranch.get();
           branch.getCars().add(currentCar.get());

            carRepository.save(car);
            branchRepository.save(branch);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{branchId}/delete/{carId}")
    public ResponseEntity deleteCarFromBranch(@PathVariable Long branchId, @PathVariable Long carId)
    {
        Optional<Branch> currentBranch = branchRepository.findById(branchId);
        Optional<Car> currentCar = carRepository.findById(carId);
        if(currentBranch.isPresent() && currentCar.isPresent()){
            Car car = currentCar.get();
            car.setOwnerBranch(null);

            Branch branch =  currentBranch.get();
            branch.getCars().remove(car);

            carRepository.save(car);
            branchRepository.save(branch);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity deleteBranch(@PathVariable Long branchId)
    {
        branchRepository.deleteById(branchId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Link createBranchSelfLink(Long branchId) {
        Link linkSelf = linkTo(methodOn(BranchController.class).getBranchById(branchId)).withSelfRel();
        return linkSelf;
    }

    private Link createBranchCarsLink(Long branchId) {
        Link linkSelf = linkTo(methodOn(BranchController.class).getCarsListByBranchId(branchId)).withSelfRel();
        return linkSelf;
    }
}
