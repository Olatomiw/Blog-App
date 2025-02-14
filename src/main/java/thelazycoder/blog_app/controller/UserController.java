package thelazycoder.blog_app.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thelazycoder.blog_app.config.CloudinaryService;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.service.UserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Tag(name = "USER", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/auth/")
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;


    public UserController(UserService userService, CloudinaryService cloudinaryService) throws InvalidInputException {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/")
    public String welcome(){
        return "Welcome";
    }
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?>    signUpUser(@Valid @RequestPart("data") UserDto userDto, @RequestPart("image") MultipartFile multipartFile) throws IOException {
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
}
