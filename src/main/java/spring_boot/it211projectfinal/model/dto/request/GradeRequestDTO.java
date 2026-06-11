package spring_boot.it211projectfinal.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeRequestDTO {
    private Long submissionId;

    private Double score;

    private String feedback;
}
