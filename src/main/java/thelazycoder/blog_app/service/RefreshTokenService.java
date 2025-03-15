package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import thelazycoder.blog_app.model.RefreshToken;
import thelazycoder.blog_app.repository.RefreshTokenRepository;
import thelazycoder.blog_app.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email).orElseThrow(
                        ()->new UsernameNotFoundException("User not found")))
                .token(UUID.randomUUID().toString())
                .expiryDate(from(Instant.now().plusMillis(600000)))
                .build();
        RefreshToken save = refreshTokenRepository.save(refreshToken);
        return ResponseEntity.ok(save);
    }
    public ResponseEntity<?> validateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(from(Instant.now()))) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Expired or invalid refresh token");
        }
        return ResponseEntity.ok(refreshToken);
    }
}
