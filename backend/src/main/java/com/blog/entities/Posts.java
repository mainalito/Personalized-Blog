package com.blog.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false ,length = 5000)
    private String description;

    private String postImageUrl;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "register_user_id")
    private RegisterUser registerUser;

    @Column(name = "category")
    private String category;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Posts posts = (Posts) o;
        return id != null && Objects.equals(id, posts.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}