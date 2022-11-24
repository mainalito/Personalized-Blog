package com.blog.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "registerUser")
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_loggedin")
    private Boolean isLoggedin = false;



    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegisterUser that = (RegisterUser) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}