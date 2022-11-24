package com.blog.repository;

import com.blog.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByName(String role_user);
}
