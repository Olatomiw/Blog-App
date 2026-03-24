package thelazycoder.blog_app.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class CloudinaryService {
    final Cloudinary cloudinary;
    private final Map<String, Object> valuesMap = new HashMap<>();

    public CloudinaryService(Environment environment) {
        valuesMap.put("cloud_name", "dhtpu4w04");
        valuesMap.put("api_key", environment.getProperty("CLOUDINARY_KEY"));
        valuesMap.put("api_secret", environment.getProperty("CLOUDINARY_SECRET"));
        valuesMap.put("secure", true);
        cloudinary = new Cloudinary(valuesMap);
    }

    @Async
    public CompletableFuture<Map> uploadFile(MultipartFile file) throws IOException {
        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return CompletableFuture.completedFuture(result);
    }


    private File convert(MultipartFile multipartFile) throws IOException {
        // Ensure the multipart file is not null
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("MultipartFile is null or empty");
        }

        // Create a temporary file in the default temporary directory
        File file = File.createTempFile("temp-", "-" + multipartFile.getOriginalFilename());

        // Use try-with-resources to ensure the FileOutputStream is closed
        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(multipartFile.getBytes());
        }

        return file;
    }
}
