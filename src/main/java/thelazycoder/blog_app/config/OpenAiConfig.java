package thelazycoder.blog_app.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

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

    @Bean
    public RetryTemplate aiRetryTemplate() {
        return RetryTemplate.builder()
                .fixedBackoff(2000) // Wait 2 seconds before retrying
                .maxAttempts(3)
                .retryOn(NonTransientAiException.class)
                .build();
    }
}
