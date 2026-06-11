package spring_boot.it211projectfinal.service;

import spring_boot.it211projectfinal.model.entity.RefreshToken;
import spring_boot.it211projectfinal.model.entity.User;

public interface RefreshTokenService {
    String createRefreshToken(User user);

    RefreshToken verifyToken(String token);
}
