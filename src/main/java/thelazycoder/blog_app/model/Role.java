package thelazycoder.blog_app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static thelazycoder.blog_app.model.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE,
                    USER_READ
            )
    ),
    USER(
            Set.of(
                    USER_CREATE,
                    USER_UPDATE,
                    USER_DELETE,
                    USER_READ
            )
    );


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        System.out.println(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
