package spring_boot.it211projectfinal.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureMaterialRequestDTO {
    private String title;

    private String materialUrl;

    private Long courseId;
}
