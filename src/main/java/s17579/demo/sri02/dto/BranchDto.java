package s17579.demo.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDto extends RepresentationModel<BranchDto> {
    private Long id;
    @NotBlank(message = "Błędna nazwa oddzialu")
    @Size(min = 2, max = 120)
    private String branchName;
}
