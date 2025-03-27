package thelazycoder.blog_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import thelazycoder.blog_app.model.Post;
import thelazycoder.blog_app.repository.PostRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;


    public void sendPostUpdate(Object payload){
        messagingTemplate.convertAndSend("/topic/update", payload);
    };

    public void updateUserPost(Object payload){
        messagingTemplate.convertAndSend("/topic/getUserPosts", payload);
    }

}
