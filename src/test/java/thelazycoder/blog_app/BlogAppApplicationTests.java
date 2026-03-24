package thelazycoder.blog_app;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import thelazycoder.blog_app.config.CloudinaryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogAppApplication.class)
@ActiveProfiles("test")
class BlogAppApplicationTests {

	@Test
	void contextLoads() {
	}

    @MockitoBean
    private JavaMailSender mailSender;

    @MockitoBean
    private CloudinaryService cloudinaryService; // or whatever you named it

    @MockitoBean
    private ChatClient chatClient;

}
