package thelazycoder.blog_app.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thelazycoder.blog_app.config.CloudinaryService;
import thelazycoder.blog_app.config.JWT.JwtService;
import thelazycoder.blog_app.dto.request.AuthDto;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.service.UserService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


@Tag(name = "USER", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/auth/")
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserController(UserService userService, CloudinaryService cloudinaryService,
                          AuthenticationManager authenticationManager, JwtService jwtService) throws InvalidInputException {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public String welcome(){
        return "Welcome";
    }
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?>signUpUser(@Valid @RequestPart(value ="data") @JsonAlias("Data") UserDto userDto,
                                       @RequestPart("image") MultipartFile multipartFile) throws IOException {
        System.out.println(userDto);
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if (bufferedImage == null) {
            return new ResponseEntity<>("Invalid image", HttpStatus.BAD_REQUEST);
        }
        return userService.signUp(userDto, multipartFile);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
       try{
           if (file.isEmpty()){
               return ResponseEntity.badRequest().body("File is empty");
           }
           BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
           if (bufferedImage == null) {
               return new ResponseEntity<>("Invalid image", HttpStatus.BAD_REQUEST);
           }
           System.out.println("MultipartFile: " + file);
           System.out.println("Filename: " + file.getOriginalFilename());
           System.out.println("Size: " + file.getSize());
           Map map = cloudinaryService.uploadFile(file);
           String url = map.get("url").toString();
           return new ResponseEntity<>(url, HttpStatus.CREATED);
       }catch (IOException e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
       }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody AuthDto authDto){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                authDto.username(), authDto.password()
        ));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        String token = jwtService.createToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }
}
