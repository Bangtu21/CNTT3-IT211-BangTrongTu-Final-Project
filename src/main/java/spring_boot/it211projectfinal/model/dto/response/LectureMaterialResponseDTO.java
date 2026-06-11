package spring_boot.it211projectfinal.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LectureMaterialResponseDTO {
    private Long id;

    private String title;

    private String materialUrl;

    private Long courseId;
}
