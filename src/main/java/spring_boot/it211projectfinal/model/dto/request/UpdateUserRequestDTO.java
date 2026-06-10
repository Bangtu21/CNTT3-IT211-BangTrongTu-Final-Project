package spring_boot.it211projectfinal.model.dto.request;

import lombok.Data;
import spring_boot.it211projectfinal.model.enums.Role;

@Data
public class UpdateUserRequestDTO {
    private String fullName;

    private String email;

    private String username;

    private Role role;
}
