package spring_boot.it211projectfinal.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Tên đầy đủ không được để trống")
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Size(min = 6, message = "Mật khẩu ít nhất 6 ký tự")
    private String password;
}
