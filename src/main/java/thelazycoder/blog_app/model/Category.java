package thelazycoder.blog_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private String id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts = new HashSet<Post>();



}
