package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.model.dto.request.LoginRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.RefreshTokenRequestDTO;
import spring_boot.it211projectfinal.model.dto.request.RegisterRequestDTO;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.model.entity.BlacklistedToken;
import spring_boot.it211projectfinal.model.entity.RefreshToken;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.model.enums.Role;
import spring_boot.it211projectfinal.repository.BlacklistedTokenRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.security.JwtUtil;
import spring_boot.it211projectfinal.service.AuthService;
import spring_boot.it211projectfinal.service.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email existed");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        userRepository.save(user);

        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
    @Override
    public AuthResponseDTO login(
            LoginRequestDTO request) {

        User user =
                userRepository.findByEmail(
                                request.getEmail())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Email not found"));

        boolean matched =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!matched) {
            throw new RuntimeException(
                    "Wrong password");
        }

        String accessToken =
                jwtUtil.generateToken(user);

        String refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponseDTO refreshToken(
            RefreshTokenRequestDTO request) {

        RefreshToken refreshToken =
                refreshTokenService
                        .verifyToken(
                                request.getRefreshToken());

        User user =
                refreshToken.getUser();

        String accessToken =
                jwtUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken())
                .build();
    }

    @Override
    public void logout(
            String token) {

        BlacklistedToken blacklistedToken =
                BlacklistedToken.builder()
                        .token(token)
                        .expiryDate(
                                jwtUtil
                                        .extractExpiration(
                                                token)
                                        .toInstant())
                        .build();

        blacklistedTokenRepository
                .save(blacklistedToken);
    }

}
