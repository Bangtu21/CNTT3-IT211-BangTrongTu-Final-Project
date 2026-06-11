package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.dto.request.*;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    void logout(String token);

    void changePassword(
            ChangePasswordRequestDTO request);

    String forgotPassword(
            ForgotPasswordRequestDTO request);

    void resetPassword(
            ResetPasswordRequestDTO request);
}
