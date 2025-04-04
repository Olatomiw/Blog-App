package thelazycoder.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thelazycoder.blog_app.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
