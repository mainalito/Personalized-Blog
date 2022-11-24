package com.blog.dtos;

import com.blog.entities.RegisterUser;

import java.io.Serializable;

/**
 * A DTO for the {@link RegisterUser} entity
 */
public record LoginDto(String username, String password) implements Serializable {
}