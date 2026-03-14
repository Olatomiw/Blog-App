package thelazycoder.blog_app.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.response.SummarizeResponse;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.repository.PostRepository;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatClientController {

    private final ChatClient chatClient;
    private final PostRepository postRepository;

    public ChatClientController(ChatClient chatClient, PostRepository postRepository){
        this.chatClient = chatClient;
        this.postRepository = postRepository;
    }


    @GetMapping("/{postId}/ai")
    public SummarizeResponse generateText(@PathVariable String postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoEntityFoundException("Not found"));
        return chatClient.prompt()
                // Injecting DB metadata (Title and Tags) so the AI is "aware" of the database record
                .user(u -> u.text("""
                        Please summarize the following post:
                        Title: {title}
                        Content: {content}
                        """)
                        .param("title", post.getTitle())
                        .param("content", post.getContent()))
                .call()
                .entity(SummarizeResponse.class);
    }
}
