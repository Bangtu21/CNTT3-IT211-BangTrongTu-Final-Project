package spring_boot.it211projectfinal.service;

import org.springframework.data.domain.Page;
import spring_boot.it211projectfinal.model.dto.request.UpdateUserRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.UserRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO create(UserRequestDTO request);

    Page<UserResponseDTO> getAll(int page, int size);

    Page<UserResponseDTO> search(String keyword, int page, int size);

    UserResponseDTO update(Long id, UpdateUserRequestDTO request);

    void delete(Long id);
}
