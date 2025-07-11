package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import thelazycoder.blog_app.config.CloudinaryService;
import thelazycoder.blog_app.config.JWT.JwtService;
import thelazycoder.blog_app.dto.request.AuthDto;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.dto.response.ApiResponse;
import thelazycoder.blog_app.dto.response.AuthResponse;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.dto.response.UserData;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.mapper.ModelMapper;
import thelazycoder.blog_app.model.Role;
import thelazycoder.blog_app.model.User;
import thelazycoder.blog_app.repository.UserRepository;
import thelazycoder.blog_app.utils.GenericFieldValidator;
import thelazycoder.blog_app.utils.InfoGetter;
import thelazycoder.blog_app.utils.ResponseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static thelazycoder.blog_app.mapper.ModelMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenericFieldValidator genericFieldValidator;
    private final CloudinaryService cloudinaryService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final InfoGetter infoGetter;
    private final WebSocketService webSocketService;
    private final EmailService emailService;

    @Transactional
    public ResponseEntity<?> signUp(final UserDto userDto, MultipartFile multipartFile){
        User user = mapDtoToModel(userDto);
        try {
            var result = cloudinaryService.uploadFile(multipartFile);
            user.setId(UUID.randomUUID().toString());
            user.setImage((String) result.get("secure_url"));
            user.setRole(Role.ADMIN);
            user.setCreated(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User validate = genericFieldValidator.validate(user);
            User save = userRepository.save(validate);

            UserData userData = mapToUserData(save);
            ApiResponse<UserData> apiResponse = new ApiResponse<>(
                    "Successfully Registered",
                    "User Registered",
                    userData
            );
            AuthDto authDto = new AuthDto(userDto.email(), userDto.password());
            emailService.sendEmail(user.getEmail(),
                    "SIGN UP SUCCESSFUL",
                    "You have successfully SignedUp to BLOGAI");
            return new ResponseEntity<>(login(authDto), HttpStatus.OK);
        }catch (InvalidInputException e){
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");

        }
    }

    @Transactional
    @jakarta.transaction.Transactional
    public ResponseEntity<?> login(AuthDto authDto){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.email() , authDto.password()
                ));
        UserData userData = null;
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user =userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("unavailable"));
            userData = mapToUserData(user);
        }
        String token = jwtService.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(
                token, userData
        );
        webSocketService.updateUserPost(authResponse);
        return new ResponseEntity<>(ResponseUtil.success(authResponse, "logged success"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getLoggedInUserPosts(){
        User loggedInUser = infoGetter.getLoggedInUser();
        List<PostResponse> collect = loggedInUser.getPosts().stream().map(
                post -> mapToPostResponse(post)
        ).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }
}
