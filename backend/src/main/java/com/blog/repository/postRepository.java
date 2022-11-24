package com.blog.repository;

import com.blog.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface postRepository extends JpaRepository<Posts, Long> {

    @Modifying
    @Query(value = "delete from posts p where p.id=:postId and p.register_user_id=:userId", nativeQuery = true)
    void deletePostsById(Long postId, Long userId);

    @Query("select (count(p) > 0) from Posts p where p.id = ?1 and p.registerUser.id = ?2")
    boolean existsByIdAndRegisterUser_Id(Long id, Long id1);


    @Query("select p from Posts p where p.category = ?1")
    List<Posts> findByCategory(String category);





}
