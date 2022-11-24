package com.blog.repository;

import com.blog.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postImageRepository extends JpaRepository<Images,Long> {
}
