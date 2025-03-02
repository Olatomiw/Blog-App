package thelazycoder.blog_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOriginPatterns("*","http://127.0.0.1:5500" )
                    .allowedMethods("PUT", "DELETE", "POST", "GET");

//        TODO add more mapping
        }
}
