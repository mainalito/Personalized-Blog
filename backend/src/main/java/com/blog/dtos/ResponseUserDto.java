package com.blog.dtos;

import com.blog.entities.RegisterUser;

import java.io.Serializable;

/**
 * A DTO for the {@link RegisterUser} entity
 */
public record ResponseUserDto(String username, String email, String imageUrl) implements Serializable {
}