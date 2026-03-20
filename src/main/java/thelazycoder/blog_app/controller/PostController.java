package thelazycoder.blog_app.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import thelazycoder.blog_app.dto.request.PostRequestDto;
import thelazycoder.blog_app.dto.response.PostResponse;
import thelazycoder.blog_app.service.PostService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Tag(name = "POST API", description = "Endpoints for managing posts")
@RestController
@RequestMapping("/api/post")
@SecurityRequirement(name = "basicAuth")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create a new post",
            description = "This API creates a new post in the blog.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping("/create")
    public ResponseEntity<PostResponse> post(@Valid @RequestPart(value = "PostBody")
                                                 @JsonAlias("PostBody") PostRequestDto postRequestDto,
                                  @RequestPart(value = "image") MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) {
            throw new IOException("Invalid image file");
        }
        PostResponse postResponse = null;
        try {
            postResponse = postService.create(postRequestDto, file);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(postResponse);
    }

    @Operation(
            summary = "Fetch Blog Post",
            description = "This API fetches all the Post in the App",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping("/getAllPost")
    public ResponseEntity<?> getPosts() {
        return postService.findAllPost();
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable String id) {
        PostResponse postById = postService.getPostById(id);
        return ResponseEntity.ok(postById);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePostById(@PathVariable String id){
        return postService.deletePostById(id);
    }
}
