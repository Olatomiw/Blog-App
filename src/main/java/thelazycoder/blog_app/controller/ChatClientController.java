package thelazycoder.blog_app.controller;

import org.springframework.web.bind.annotation.*;
import thelazycoder.blog_app.dto.response.SummarizeResponse;
import thelazycoder.blog_app.service.SummarizerService;

@RestController
@RequestMapping("/api")
public class ChatClientController {

   private final SummarizerService summarizerService;

    public ChatClientController(SummarizerService summarizerService){
        this.summarizerService = summarizerService;
    }


    @GetMapping("/{postId}/ai")
    public SummarizeResponse generateText(@PathVariable String postId){
        return summarizerService.generateSummary(postId);
    }
}
