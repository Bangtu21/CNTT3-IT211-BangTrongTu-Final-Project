package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.dto.request.LoginRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.RefreshTokenRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.RegisterRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);

    void logout(String token);
}
