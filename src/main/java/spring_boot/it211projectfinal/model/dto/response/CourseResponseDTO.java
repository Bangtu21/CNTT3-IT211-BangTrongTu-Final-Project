package spring_boot.it211projectfinal.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseResponseDTO {
    private Long id;

    private String courseName;

    private String description;
}
