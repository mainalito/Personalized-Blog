package com.blog.services.registerUser;

import com.blog.entities.RegisterUser;

import java.util.Optional;

public interface RegisterUserMethods {
    RegisterUser saveUser(RegisterUser registerUser);
    void deleteUser(Long id);
    RegisterUser getUser(Long id);
}
