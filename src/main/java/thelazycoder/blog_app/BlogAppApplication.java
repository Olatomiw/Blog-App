package thelazycoder.blog_app;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class BlogAppApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
