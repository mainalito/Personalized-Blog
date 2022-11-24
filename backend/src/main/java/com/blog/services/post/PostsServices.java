package com.blog.services.post;

import com.blog.entities.Posts;
import com.blog.repository.RegistrationRepository;
import com.blog.repository.postRepository;
import com.blog.services.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostsServices implements PostMethods {
    private final postRepository repository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Posts createPost(Posts posts) {

        return repository.save(posts);
    }

    @Override
    public Posts getPost(Long id) {
        Optional<Posts> optionalPosts = repository.findById(id);
        return optionalPosts.orElse(null);
    }

    @Override
    public List<Posts> getAllPosts() {
        return repository.findAll();
    }

    @Override
    public Posts updatePost(Posts posts, Long id, MultipartFile multipartFile) throws IOException {

        Optional<Posts> optionalPosts = repository.findById(id);
        if (optionalPosts.isPresent()) {
            Posts updatePost = optionalPosts.get();
            updatePost.setDate(posts.getDate());
            updatePost.setTitle(posts.getTitle());
            updatePost.setDescription(posts.getDescription());

            String url = cloudinaryService.upload(multipartFile);
            posts.setPostImageUrl(url);
            updatePost.setPostImageUrl(posts.getPostImageUrl());
            updatePost.setCategory(posts.getCategory());
            updatePost.setRegisterUser(posts.getRegisterUser());
            return repository.save(updatePost);
        }
        throw new RuntimeException("Unable to update");
    }

    @Override
    public String deletePost(Long postId, Long userId) {
        repository.deletePostsById(postId, userId);
        return "Deleted post with id " + postId;

    }

    @Override
    public List<Posts> searchByCategory(String cat) {
        return repository.findByCategory(cat);
    }
}
