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

    public CloudinaryService(
            @Value("${cloudinary.api-key}") String cloudinaryKey,
            @Value("${cloudinary.api-secret}") String cloudinarySecret,
            @Value("${cloudinary.cloud-name}") String cloudinaryCloudName) {
        valuesMap.put("cloud_name", cloudinaryCloudName);
        valuesMap.put("api_key", cloudinaryKey);
        valuesMap.put("api_secret", cloudinarySecret);
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
