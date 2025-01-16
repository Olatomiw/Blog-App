package thelazycoder.blog_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.request.UserDto;
import thelazycoder.blog_app.exception.InvalidInputException;
import thelazycoder.blog_app.service.UserService;

@RestController
@RequestMapping("/api/")
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
