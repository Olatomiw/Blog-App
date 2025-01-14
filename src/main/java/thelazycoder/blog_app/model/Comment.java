package thelazycoder.blog_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    private String text;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
