package com.blog.controllers;

import com.blog.Responses.ResponseHandler;
import com.blog.dtos.CreatePostDto;
import com.blog.dtos.PostsDto;
import com.blog.entities.Posts;
import com.blog.entities.RegisterUser;
import com.blog.repository.RegistrationRepository;
import com.blog.repository.postRepository;
import com.blog.services.cloudinary.CloudinaryService;
import com.blog.services.post.PostsServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/posts")
public class PostController {
    //TODO:USE DTOS TO SHOW SPECIFIC DATA RATHER THAN ALL DATA FROM DATABASE ESPECIALLY USER DATA.
    //TODO:CREATE A FEATURE FOR A USER TO UPDATE THEIR PROFILE PICTURE
    private final PostsServices postsServices;
    private final postRepository repository;
    private final RegistrationRepository registrationRepository;
    private final CloudinaryService cloudinaryService;


    @CrossOrigin
    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes =
            {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE
            })
    public ResponseEntity<?> savePost(@RequestPart("post") @Validated CreatePostDto createPostDto,
                                      @RequestPart(name = "imagefile", required = false) MultipartFile multipartFile) {
        try {

            Posts posts = CreateAndUpdatePost(createPostDto);
            //set cloudinary url
            String secure_url = cloudinaryService.upload(multipartFile);
            posts.setPostImageUrl(secure_url);
            // save to database
            Posts servicesPost = postsServices.createPost(posts);
            PostsDto postsDto = new PostsDto(servicesPost.getId(), servicesPost.getTitle(), servicesPost.getDescription(), servicesPost.getPostImageUrl(), servicesPost.getDate(), servicesPost.getRegisterUser().getId(), servicesPost.getRegisterUser().getUsername(), servicesPost.getRegisterUser().getEmail(), servicesPost.getRegisterUser().getImageUrl(), servicesPost.getCategory());

            return ResponseHandler.generateResponse("successful saved a post!! ", HttpStatus.CREATED, postsDto);
        } catch (Exception e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestPart("post") @Validated CreatePostDto createPostDto,
                                        @RequestPart(name = "imagefile", required = false) MultipartFile multipartFile) {
        try {

            Posts posts = CreateAndUpdatePost(createPostDto);

            Posts servicesPost = postsServices.updatePost(posts, id,multipartFile);

            return ResponseHandler.generateResponse("Updated Successfully!!", HttpStatus.OK, servicesPost);
        } catch (IllegalStateException | IOException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    private Posts CreateAndUpdatePost(CreatePostDto createPostDto) {

        Posts posts = new Posts();
        Optional<RegisterUser> userOptional = registrationRepository.findById(createPostDto.registerUserId());
        if (userOptional.isPresent()) {
            RegisterUser registerUser = userOptional.get();
            posts.setRegisterUser(registerUser);
        }
        posts.setTitle(createPostDto.title());
        posts.setDescription(createPostDto.description());
        posts.setDate(LocalDate.now());
        posts.setCategory(createPostDto.category());
        posts.setPostImageUrl(createPostDto.postImageUrl());
        return posts;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts(@RequestParam Optional<String> cat) {
        try {

            if (cat.isPresent()) {
                List<Posts> categoryList = postsServices.searchByCategory(cat.get());


                List<PostsDto> postsDtoList = categoryList.stream().map(posts -> new PostsDto(posts.getId(), posts.getTitle(), posts.getDescription(), posts.getPostImageUrl(), posts.getDate(), posts.getRegisterUser().getId(), posts.getRegisterUser().getUsername(), posts.getRegisterUser().getEmail(), posts.getRegisterUser().getImageUrl(), posts.getCategory())).collect(Collectors.toList());
                return ResponseHandler.generateResponse("category found", HttpStatus.OK, postsDtoList);

            }
            List<Posts> allPosts = postsServices.getAllPosts();
            List<PostsDto> allDtoPosts = allPosts.stream().map(posts -> new PostsDto(posts.getId(), posts.getTitle(), posts.getDescription(), posts.getPostImageUrl(), posts.getDate(), posts.getRegisterUser().getId(), posts.getRegisterUser().getUsername(), posts.getRegisterUser().getEmail(), posts.getRegisterUser().getImageUrl(), posts.getCategory())).collect(Collectors.toList());

            return ResponseHandler.generateResponse("no category found", HttpStatus.OK, allDtoPosts);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Posts posts = postsServices.getPost(id);
        PostsDto postsDto = new PostsDto(posts.getId(), posts.getTitle(), posts.getDescription(), posts.getPostImageUrl(), posts.getDate(), posts.getRegisterUser().getId(), posts.getRegisterUser().getUsername(), posts.getRegisterUser().getEmail(), posts.getRegisterUser().getImageUrl(), posts.getCategory());
        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/userId/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @PathVariable Long userId) {
        try {
            Optional<RegisterUser> userOptional = registrationRepository.findById(userId);
            if (userOptional.isPresent()) {
                RegisterUser registerUser = userOptional.get();
                if (registerUser.getIsLoggedin()) {
                    boolean b = repository.existsByIdAndRegisterUser_Id(id, userId);
                    if (b) {
                        postsServices.deletePost(id, userId);
                        return ResponseHandler.generateResponse("Deleted!!", HttpStatus.OK, null);
                    }
                    return ResponseHandler.generateResponse("Unable to delete", HttpStatus.BAD_REQUEST, null);
                }
            }
            return ResponseHandler.generateResponse("Unable to delete! Please login first", HttpStatus.BAD_REQUEST, null);
        } catch (IllegalStateException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
