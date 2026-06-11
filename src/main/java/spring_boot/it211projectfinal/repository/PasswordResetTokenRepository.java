package spring_boot.it211projectfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot.it211projectfinal.model.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
