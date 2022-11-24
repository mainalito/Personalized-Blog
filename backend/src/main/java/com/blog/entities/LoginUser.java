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
public class LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private  String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "loginUser_authorities",
            joinColumns = @JoinColumn(name = "loginUser_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Set<Authority> authorities = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LoginUser loginUser = (LoginUser) o;
        return id != null && Objects.equals(id, loginUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
