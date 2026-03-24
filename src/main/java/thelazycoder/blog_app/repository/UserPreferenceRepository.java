package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.UserPreference;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    Optional<UserPreference> findByUserId(String userId);
}
