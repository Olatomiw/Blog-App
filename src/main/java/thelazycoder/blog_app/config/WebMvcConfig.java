package thelazycoder.blog_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOriginPatterns("http://localhost:3000" )
                    .allowedHeaders("Content-Type", "Authorization")
                    .allowedMethods("PUT", "DELETE", "POST", "GET")
                    .allowCredentials(true);

//        TODO add more mapping
        }
}
