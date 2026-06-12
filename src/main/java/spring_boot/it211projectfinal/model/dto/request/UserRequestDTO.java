package spring_boot.it211projectfinal.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import spring_boot.it211projectfinal.model.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
