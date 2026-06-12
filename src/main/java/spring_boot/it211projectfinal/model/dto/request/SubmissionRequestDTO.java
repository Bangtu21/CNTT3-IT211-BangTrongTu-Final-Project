package spring_boot.it211projectfinal.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class SubmissionRequestDTO {
    private Long courseId;
    private String githubLink;
}
