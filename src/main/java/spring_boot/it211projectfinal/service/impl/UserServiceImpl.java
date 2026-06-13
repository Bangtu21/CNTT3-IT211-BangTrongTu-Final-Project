package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.exeption.ResourceNotFoundException;
import spring_boot.it211projectfinal.model.dto.request.UpdateUserRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.UserRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .build();

        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public Page<UserResponseDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Page<UserResponseDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findByFullNameContainingIgnoreCase(keyword, pageable).map(this::mapToResponse);
    }

    @Override
    public UserResponseDTO update(Long id, UpdateUserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setRole(request.getRole());

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    private UserResponseDTO mapToResponse(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
