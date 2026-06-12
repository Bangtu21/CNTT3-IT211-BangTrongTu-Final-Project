package spring_boot.it211projectfinal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import spring_boot.it211projectfinal.model.dto.request.LoginRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.RegisterRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.model.enums.Role;
import spring_boot.it211projectfinal.repository.BlacklistedTokenRepository;
import spring_boot.it211projectfinal.repository.PasswordResetTokenRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.security.JwtAuthenticationFilter;
import spring_boot.it211projectfinal.security.JwtUtil;
import spring_boot.it211projectfinal.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    void register_success() {

        RegisterRequestDTO request =
                RegisterRequestDTO.builder()
                        .fullName("Bang")
                        .email("test@gmail.com")
                        .username("bang")
                        .password("123456")
                        .build();

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(
                request.getPassword()))
                .thenReturn("encoded");

        UserResponseDTO response =
                authService.register(request);

        assertNotNull(response);

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    void register_duplicate_email() {

        RegisterRequestDTO request =
                RegisterRequestDTO.builder()
                        .email("test@gmail.com")
                        .build();

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );
    }

    @Test
    void login_success() {

        User user =
                User.builder()
                        .id(1L)
                        .email("bang@gmail.com")
                        .password("encoded")
                        .role(Role.STUDENT)
                        .build();

        LoginRequestDTO request =
                LoginRequestDTO.builder()
                        .email("bang@gmail.com")
                        .password("123456")
                        .build();

        when(userRepository.findByEmail(
                request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "123456",
                "encoded"))
                .thenReturn(true);

        when(jwtUtil.generateToken(user))
                .thenReturn("jwt-token");

        when(refreshTokenService
                .createRefreshToken(user))
                .thenReturn("refresh-token");

        AuthResponseDTO response =
                authService.login(request);

        assertNotNull(response);

        assertEquals(
                "jwt-token",
                response.getAccessToken());

        assertEquals(
                "refresh-token",
                response.getRefreshToken());
    }

    @Test
    void login_invalid_password() {

        User user =
                User.builder()
                        .email("bang@gmail.com")
                        .password("encoded")
                        .build();

        LoginRequestDTO request =
                LoginRequestDTO.builder()
                        .email("bang@gmail.com")
                        .password("wrong")
                        .build();

        when(userRepository.findByEmail(
                request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                anyString(),
                anyString()))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );
    }
}
