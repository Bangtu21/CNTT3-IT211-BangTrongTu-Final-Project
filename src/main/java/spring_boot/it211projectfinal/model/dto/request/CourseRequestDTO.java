package spring_boot.it211projectfinal.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseRequestDTO {
    @NotBlank
    private String courseName;

    private String description;
}
