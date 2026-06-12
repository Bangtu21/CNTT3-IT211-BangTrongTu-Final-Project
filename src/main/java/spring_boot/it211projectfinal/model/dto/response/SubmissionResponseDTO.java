package spring_boot.it211projectfinal.model.dto.response;

import lombok.*;
import spring_boot.it211projectfinal.model.enums.SubmissionStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class SubmissionResponseDTO {
    private Long id;

    private String githubLink;

    private String reportUrl;

    private Double score;

    private String feedback;

    private SubmissionStatus status;

    private LocalDateTime submittedAt;

    private Long studentId;

    private Long courseId;
}
