package com.blog.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    private byte[] imageData;
    private String imageName;
    private String imageType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Images images = (Images) o;
        return id != null && Objects.equals(id, images.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}