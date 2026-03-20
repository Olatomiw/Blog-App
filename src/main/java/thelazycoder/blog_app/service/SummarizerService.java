package thelazycoder.blog_app.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import thelazycoder.blog_app.dto.response.SummarizeResponse;
import thelazycoder.blog_app.exception.NoEntityFoundException;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.repository.PostRepository;

@Service
public class SummarizerService {

    @Autowired
    private CacheManager cacheManager;
    private final ChatClient chatClient;
    private final PostRepository postRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SummarizerService.class);

    public SummarizerService(ChatClient chatClient, PostRepository postRepository){
        this.chatClient = chatClient;
        this.postRepository = postRepository;
    }

    @Cacheable(value = "summary", key = "#postId")
    public SummarizeResponse generateSummary(String postId){
        log.info(">>> METHOD EXECUTED for postId: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoEntityFoundException("Not found"));
        SummarizeResponse response =chatClient.prompt()
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
        return response;
    }

    @PostConstruct
    public void testCache(){
        System.out.println("CacheManager= "+ cacheManager.getClass());
    }
}
