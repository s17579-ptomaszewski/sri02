package s17579.demo.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import s17579.demo.sri02.model.Branch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CarDetailsDto extends RepresentationModel<CarDetailsDto> {
    private Long id;
    @NotBlank(message = "Błędna nazwa modelu")
    @Size(min = 2, max = 120)
    private String modelName;
    @NotNull
    private LocalDate createDate;
    private int doorNumber;
    @NotBlank
    private String color;

    private BranchDto branch;
}