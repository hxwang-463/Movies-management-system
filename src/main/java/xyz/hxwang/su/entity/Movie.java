package xyz.hxwang.su.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="release_date")
    private Date releaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "series")
    private Series series;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "movie_model",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id"))
    private Set<Model> models = new HashSet<>();

    public void addModel(Model model) {
        this.models.add(model); // Add the model to the movie's set of models.
        model.getMovies().add(this); // Add this movie to the model's set of movies.
    }

    public void removeModel(Model model) {
        this.models.remove(model); // Remove the model from the movie's set of models.
        model.getMovies().remove(this); // Remove this movie from the model's set of movies.
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + releaseDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
