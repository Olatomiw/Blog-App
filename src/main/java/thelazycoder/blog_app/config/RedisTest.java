package thelazycoder.blog_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisTest implements CommandLineRunner {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void run(String... args) {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            String pong = connection.ping();
            System.out.println("✅ Connected to Redis! Response: " + pong);
        } catch (Exception e) {
            System.err.println("❌ Redis connection failed: " + e.getMessage());
        }
    }
}