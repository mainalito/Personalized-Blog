package com.blog.repository;


import com.blog.entities.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegisterUser, Long> {
    Optional<RegisterUser> findByUsername(String username);

    @Query("select (count(r) > 0) from RegisterUser r where r.username = ?1")
    boolean existsByUsername(String username);

    @Query("select (count(r) > 0) from RegisterUser r where r.email = ?1")
    boolean existsByEmail(String email);


}
