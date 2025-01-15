package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thelazycoder.blog_app.dto.UserDto;
import thelazycoder.blog_app.dto.response.ApiResponse;
import thelazycoder.blog_app.dto.response.UserData;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.mapper.ModelMapper;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.UserRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericFieldValidator genericFieldValidator;


    @Transactional
    public ResponseEntity<?> signUp(UserDto userDto){
        ModelMapper modelMapper = new ModelMapper(passwordEncoder);
        User user = modelMapper.mapDtoToModel(userDto);
        try {
            user.setId(UUID.randomUUID().toString());
            user.setImage("my_image.png");
            user.setRole("ROLE_USER");
            user.setCreated(LocalDateTime.now());
            User validate = genericFieldValidator.validate(user);
            User save = userRepository.save(validate);

            UserData userData = new UserData(
                    save.getId(), save.getFirstName(),
                    save.getLastName(), save.getEmail(),
                    save.getUsername(), save.getImage(),
                    save.getCreated(), save.getRole()
            );
            ApiResponse<UserData> apiResponse = new ApiResponse<>(
                    "Successfully Registered",
                    "User Registered",
                    userData
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (InvalidInputException e){
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");

        }
    }
}
