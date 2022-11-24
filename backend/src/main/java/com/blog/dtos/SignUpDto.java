package com.blog.dtos;

import java.io.Serializable;

/**
 * A DTO for the {@link com.blog.entities.RegisterUser} entity
 */
public record SignUpDto(Long id, String username, String email, String imageUrl) implements Serializable {
}