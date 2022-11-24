package com.blog.dtos;

import com.blog.entities.RegisterUser;

import java.io.Serializable;

/**
 * A DTO for the {@link RegisterUser} entity
 */
public record RegisterUserDto(String username, String email, String password, String imageUrl) implements Serializable {
    /**
     * A DTO for the {@link RegisterUser} entity
     */
    public static record ResponseUserDto(Long id, String username, String email, String imageUrl) implements Serializable {
    }
}