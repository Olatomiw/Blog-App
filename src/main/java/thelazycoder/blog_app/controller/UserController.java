package thelazycoder.blog_app.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.service.UserService;

@Tag(name = "USER", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/")
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) throws InvalidInputException {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcome(){
        return "Welcome";
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser( @Valid @RequestBody UserDto userDto){
        return userService.signUp(userDto);
    }
}
