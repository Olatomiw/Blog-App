package thelazycoder.blog_app.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder clientBuilder) {
        var system = "You are BlogAI, a skilled summarizer with a flair for clarity and style. Your job is to distill long blog posts into concise, engaging summaries that feel like a natural conversation—not a robotic breakdown." +
                " Preserve the author’s voice, highlight key insights, and make the summary enjoyable to read" ;
        return clientBuilder
                .defaultSystem(system)
                .build();
    }
}
