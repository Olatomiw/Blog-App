package thelazycoder.blog_app.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import thelazycoder.blog_app.model.Role;
import thelazycoder.blog_app.model.User;

import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails {

    private User user;

    private final String username;
    private final String password;
    private final Role role;

    public UserDetail(User user) {
        this.username= user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
