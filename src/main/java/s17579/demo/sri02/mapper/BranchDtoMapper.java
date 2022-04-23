package s17579.demo.sri02.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import s17579.demo.sri02.dto.BranchDetailsDto;
import s17579.demo.sri02.dto.BranchDto;
import s17579.demo.sri02.dto.EmployeeDto;
import s17579.demo.sri02.model.Branch;
import s17579.demo.sri02.model.Employee;

@Component
@RequiredArgsConstructor
public class BranchDtoMapper {
    private final ModelMapper modelMapper;

    public BranchDetailsDto convertToDetailsDto(Branch e) {
        return modelMapper.map(e, BranchDetailsDto.class);
    }

    public BranchDto convertToDto(Branch e) {
        return modelMapper.map(e, BranchDto.class);
    }

    public Branch convertToEntity(BranchDto dto) {
        return modelMapper.map(dto, Branch.class);
    }
}
