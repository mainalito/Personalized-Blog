package com.blog.services.post;

import com.blog.entities.Posts;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostMethods {
    Posts createPost(Posts posts) ;
    Posts getPost(Long id);
    List<Posts> getAllPosts();
    Posts updatePost(Posts posts, Long id,MultipartFile multipartFile) throws IOException;
    String deletePost(Long postId, Long userId);

    List<Posts> searchByCategory(String s);
}
