package thelazycoder.blog_app.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import thelazycoder.blog_app.dto.UserDto;
import thelazycoder.blog_app.model.User;

@RequiredArgsConstructor
public class ModelMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapDtoToModel(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setBio(dto.bio());
        return user;
    }


}
