package com.blog.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Cloudinary cloudinary = Singleton.getCloudinary();

    public String upload(MultipartFile file) {
        try {
            Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println(uploadResult);
            String secure_url = uploadResult.get("secure_url").toString();
            logger.info(" successfully uploaded the file: " + secure_url);
            return secure_url;
        } catch (Exception ex) {
            logger.error(" failed to load to Cloudinary the image file: " );
            logger.error(ex.getMessage());
            return null;
        }
    }
}
