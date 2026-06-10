package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.dto.request.RegisterRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO request);
}
