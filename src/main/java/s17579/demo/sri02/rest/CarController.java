package s17579.demo.sri02.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import s17579.demo.sri02.dto.BranchDto;
import s17579.demo.sri02.dto.CarDetailsDto;
import s17579.demo.sri02.mapper.BranchDtoMapper;
import s17579.demo.sri02.mapper.CarDtoMapper;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Car;
import s17579.demo.sri02.dto.CarDto;
import s17579.demo.sri02.repo.CarRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private CarRepository carRepository;

    private CarDtoMapper carDtoMapper;

    private BranchDtoMapper branchDtoMapper;

    public CarController(CarRepository carRepository, CarDtoMapper carDtoMapper, BranchDtoMapper branchDtoMapper){
        this.carRepository = carRepository;
        this.carDtoMapper = carDtoMapper;
        this.branchDtoMapper = branchDtoMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<CarDto>> getCars() {
        List<Car> allCars = carRepository.findAll();
        List<CarDto> result = allCars.stream()
                .map(carDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(CarDto dto : result){
            dto.add(createCarSelfLink(dto.getId()));
            dto.add(createCarBranchLink(dto.getId()));
        }
        Link linkSelf = linkTo(methodOn(CarController.class).getCars()).withSelfRel();
        CollectionModel<CarDto> res = CollectionModel.of(result, linkSelf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> getCarById(@PathVariable Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        if(car.isPresent()) {
            CarDetailsDto carDetailsDto = carDtoMapper.convertToDtoDetails(car.get());
            Link linkSelf = createCarSelfLink(carId);
            carDetailsDto.add(linkSelf);
            return new ResponseEntity<>(carDetailsDto,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{carId}/branch")
    public ResponseEntity<BranchDto> getBranchByCarId(@PathVariable Long carId) {
       Branch branch = carRepository.findBranchByCarId(carId);
        if(branch != null) {
            BranchDto result = branchDtoMapper.convertToDto(branch);
            return new ResponseEntity<>(result,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity saveNewCar(@Valid @RequestBody CarDto car) {
        Car entity = carDtoMapper.convertToEntity(car);
        carRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{carId}")
    public ResponseEntity updateCar(@PathVariable Long carId, @RequestBody CarDto carDto) {
        Optional<Car> currentCar =
                carRepository.findById(carId);
        if(currentCar.isPresent()) {
            carDto.setId(carId);
            Car entity = carDtoMapper.convertToEntity(carDto);
            carRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity deleteCarById(@PathVariable Long carId)
    {
        carRepository.deleteById(carId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Link createCarSelfLink(Long carId) {
        Link linkSelf = linkTo(methodOn(CarController.class).getCarById(carId)).withSelfRel();
        return linkSelf;
    }

    private Link createCarBranchLink(Long carId) {
        Link linkSelf = linkTo(methodOn(CarController.class).getBranchByCarId(carId)).withSelfRel();
        return linkSelf;
    }
}
