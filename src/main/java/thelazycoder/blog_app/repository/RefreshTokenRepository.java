package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
