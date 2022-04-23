package s17579.demo.sri02.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Błędna nazwa modelu")
    @Size(min = 2, max = 120)
    private String modelName;
    @NotNull
    private LocalDate createDate;
    @NotNull
    private int doorNumber;
    @NotBlank
    private String color;

    @ManyToOne
    @JoinColumn(name = "ownerBranch_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Branch ownerBranch;
}