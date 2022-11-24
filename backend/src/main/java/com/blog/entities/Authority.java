package com.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;


    @ManyToMany(mappedBy = "authorities", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ToString.Exclude
    private Set<LoginUser> loginUsers = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authority authority = (Authority) o;
        return name != null && Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}