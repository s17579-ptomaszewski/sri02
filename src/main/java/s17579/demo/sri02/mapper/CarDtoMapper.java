package s17579.demo.sri02.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import s17579.demo.sri02.dto.CarDetailsDto;
import s17579.demo.sri02.dto.CarDto;
import s17579.demo.sri02.model.Car;

@Component
@RequiredArgsConstructor
public class CarDtoMapper {
    private final ModelMapper modelMapper;

    public CarDto convertToDto(Car e) {return modelMapper.map(e, CarDto.class);}

    public CarDetailsDto convertToDtoDetails(Car e) {return modelMapper.map(e, CarDetailsDto.class);}

    public Car convertToEntity(CarDto dto) {
        return modelMapper.map(dto, Car.class);
    }
}
