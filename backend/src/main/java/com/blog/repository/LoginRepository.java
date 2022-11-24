package com.blog.repository;

import com.blog.entities.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginUser,Long> {


    @Query("select l from LoginUser l where l.username = ?1")
    Optional<LoginUser> findByUsername(String username);

    @Query("select (count(l) > 0) from LoginUser l where l.username = ?1")
    boolean existsByUsername(String username);



}
