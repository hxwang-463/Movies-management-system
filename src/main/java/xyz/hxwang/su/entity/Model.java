package xyz.hxwang.su.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "models")
    private Set<Movie> movies = new HashSet<>();

    public Model(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
