package spring_boot.it211projectfinal.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResetPasswordRequestDTO {
    private String token;

    private String newPassword;
}
