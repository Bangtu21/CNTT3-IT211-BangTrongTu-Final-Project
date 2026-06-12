package spring_boot.it211projectfinal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring_boot.it211projectfinal.model.entity.RefreshToken;
import spring_boot.it211projectfinal.model.entity.User;
import spring_boot.it211projectfinal.repository.RefreshTokenRepository;
import spring_boot.it211projectfinal.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Override
    public String createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .user(user)
                .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Override
    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
