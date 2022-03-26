package s17579.demo.sri02.model.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String modelName;
    private LocalDate createDate;
    private int doorNumber;
    private String color;
}
