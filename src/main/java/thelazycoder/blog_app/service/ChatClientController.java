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
        String content = post.getContent();
        PromptTemplate promptTemplate= new PromptTemplate("Summarize the content following text,{content}");
        Prompt prompt = promptTemplate.create(Map.of("content", content));
        return chatClient.prompt(prompt)
                .call()
                .entity(SummarizeResponse.class);
    }
}
