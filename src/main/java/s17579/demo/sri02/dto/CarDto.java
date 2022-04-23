package s17579.demo.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto extends RepresentationModel<CarDto> {
    private Long id;
    @NotBlank(message = "Błędna nazwa modelu")
    @Size(min = 2, max = 120)
    private String modelName;
    @NotNull
    private LocalDate createDate;
    private int doorNumber;
    @NotBlank
    private String color;
}
