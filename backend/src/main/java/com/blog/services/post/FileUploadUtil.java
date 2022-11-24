package com.blog.services.post;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

        }
        catch (IOException ioException){
            throw new IOException("could not save image file " + fileName,ioException);
        }
    }

}
