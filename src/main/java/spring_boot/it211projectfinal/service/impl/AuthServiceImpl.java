package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.exeption.BadRequestException;
import spring_boot.it211projectfinal.exeption.ResourceNotFoundException;
import spring_boot.it211projectfinal.model.dto.request.*;
import spring_boot.it211projectfinal.model.dto.response.AuthResponseDTO;
import spring_boot.it211projectfinal.model.dto.response.UserResponseDTO;
import spring_boot.it211projectfinal.model.entity.BlacklistedToken;
import spring_boot.it211projectfinal.model.entity.PasswordResetToken;
import spring_boot.it211projectfinal.model.entity.RefreshToken;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.model.enums.Role;
import spring_boot.it211projectfinal.repository.BlacklistedTokenRepository;
import spring_boot.it211projectfinal.repository.PasswordResetTokenRepository;
import spring_boot.it211projectfinal.repository.UserRepository;
import spring_boot.it211projectfinal.security.JwtUtil;
import spring_boot.it211projectfinal.service.AuthService;
import spring_boot.it211projectfinal.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

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
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        boolean matched = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        if (!matched) {
            throw new BadRequestException("Wrong password");
        }

        String accessToken = jwtUtil.generateToken(user);

        String refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        RefreshToken refreshToken = refreshTokenService.verifyToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken = jwtUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public void logout(String token) {
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .expiryDate(
                        jwtUtil
                                .extractExpiration(token)
                                .toInstant())
                .build();

        blacklistedTokenRepository.save(blacklistedToken);
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public String forgotPassword(ForgotPasswordRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .build();

        passwordResetTokenRepository.save(resetToken);

        return token;
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO request) {

        PasswordResetToken tokenEntity = passwordResetTokenRepository.findByToken(request.getToken())
                        .orElseThrow(() -> new BadRequestException("Invalid token"));

        if(tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Token expired");
        }

        User user = tokenEntity.getUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        passwordResetTokenRepository.delete(tokenEntity);
    }
}
