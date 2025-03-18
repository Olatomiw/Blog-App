package thelazycoder.blog_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOriginPatterns("http://localhost:3000", "https://id-preview--0ab74246-930f-49e7-a31e-9166b80951a2.lovable.app" )
                    .allowedHeaders("Content-Type", "Authorization")
                    .allowedMethods("PUT", "DELETE", "POST", "GET")
                    .allowCredentials(true);

//        TODO add more mapping
        }
}
