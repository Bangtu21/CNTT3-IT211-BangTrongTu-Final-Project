package spring_boot.it211projectfinal.model.dto.request;

import lombok.*;
import spring_boot.it211projectfinal.model.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserRequestDTO {
    private String fullName;

    private String email;

    private String username;

    private Role role;
}
