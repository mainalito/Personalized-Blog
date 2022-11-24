package com.blog.config;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry resourceHandlerRegistry){
        exposeDirectory("user-photos",resourceHandlerRegistry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry resourceHandlerRegistry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        if(dirName.startsWith("../")) dirName = dirName.replace("../","");
        resourceHandlerRegistry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }
}
