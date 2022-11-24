package com.blog;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class BlogApplication {
    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);
    private final Environment environment;

    public static void main(String[] args) {

        SpringApplication.run(BlogApplication.class, args);

    }

    @PostConstruct
    public void cloudinaryConnection() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", environment.getProperty("cloud_name"),
                "api_key", environment.getProperty("api_key"),
                "api_secret", environment.getProperty("api_secret")));
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();
        logger.info("successfully connected to Cloudinary " + environment.getProperty("cloud_name"));
    }
}
