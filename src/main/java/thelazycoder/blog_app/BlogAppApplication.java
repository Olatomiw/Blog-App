package thelazycoder.blog_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class BlogAppApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BlogAppApplication.class, args);
	}
}
