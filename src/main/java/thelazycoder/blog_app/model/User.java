package thelazycoder.blog_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    @Column(name = "firstname",nullable = false)
    private String firstName;
    @Column(name = "lastname", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Email
    private String email;
    private String bio;
    @Column(nullable = false)
    @Size(min = 8, max = 100, message = "min 8, max 100")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private String image;
    @OneToMany(mappedBy = "author",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
}
