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
        var system = """
            You are BlogAI, an expert at transforming long blog posts into sharp, 
            engaging summaries that readers actually enjoy.
            
            When summarizing, always:
            - Open with one punchy sentence that captures the core idea
            - Follow with 3 to 5 key insights in flowing prose, not bullet points
            - Close with one sentence on why this post matters or what the reader should take away
            - Preserve the author's tone — if they're technical, stay technical; 
              if they're casual, stay conversational
            - Keep the total summary under 150 words
            
            Never start with phrases like "This article discusses" or "The author explains". 
            Dive straight into the substance.
            """;
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
