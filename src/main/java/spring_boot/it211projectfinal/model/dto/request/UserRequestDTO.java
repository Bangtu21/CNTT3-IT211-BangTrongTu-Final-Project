package spring_boot.it211projectfinal.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import spring_boot.it211projectfinal.model.enums.Role;

@Data
public class UserRequestDTO {
    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Role role;
}
