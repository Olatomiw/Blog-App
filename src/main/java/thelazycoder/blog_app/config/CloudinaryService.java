package thelazycoder.blog_app.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    @Value("${cloud_api_key}")
    private String cloud_api_key;
    @Value("${cloud_secret_key}")
    private String cloud_secret_key;

    final Cloudinary cloudinary;
    private final Map<String, Object> valuesMap = new HashMap<>();

    public CloudinaryService() {
        valuesMap.put("cloud_name", "dhtpu4w04");
        valuesMap.put("api_key", "186478295363518");
        valuesMap.put("api_secret", "UYQP32IanJQkJjN8PcykLWtRBHw");
        valuesMap.put("secure", true);
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map uploadFile(MultipartFile file) throws IOException {
        File file_name = convert(file);
        return cloudinary.uploader().upload(file_name, ObjectUtils.asMap());
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
