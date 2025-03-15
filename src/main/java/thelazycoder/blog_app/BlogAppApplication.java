package thelazycoder.blog_app;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class BlogAppApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(BlogAppApplication.class, args);
		SecureRandom random = new SecureRandom();
		byte[] keyBytes = new byte[32]; // 32 bytes = 256-bit key
		random.nextBytes(keyBytes);
		String secretKey = Base64.getEncoder().encodeToString(keyBytes);
		System.out.println("Generated Secret Key: " + secretKey);
	}

}
