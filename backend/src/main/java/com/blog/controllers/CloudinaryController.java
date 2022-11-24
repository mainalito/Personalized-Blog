package com.blog.controllers;

import com.blog.services.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/cloud")
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public @ResponseBody String uploadToCloudinary(@RequestParam("file") MultipartFile file){
        return cloudinaryService.upload(file);
    }
}
