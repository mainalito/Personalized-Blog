package com.blog.dtos;

import com.blog.entities.Posts;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Posts} entity
 */
public record CreatePostDto(String title, String description, LocalDate date,String postImageUrl, Long registerUserId,
                            String category) implements Serializable {
}