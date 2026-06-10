package spring_boot.it211projectfinal.model.dto.response;

import lombok.Builder;
import lombok.Data;
import spring_boot.it211projectfinal.model.enums.Role;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private Role role;
}
