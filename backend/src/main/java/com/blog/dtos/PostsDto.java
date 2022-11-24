package com.blog.dtos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.blog.entities.Posts} entity
 */
public record PostsDto(Long id, String title, String description, String postImageUrl, LocalDate date,
                       Long registerUserId, String registerUserUsername, String registerUserEmail,
                       String registerUserImageUrl, String category) implements Serializable {
}