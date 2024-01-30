package xyz.hxwang.su.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Long id;

    private String name;

    private Date releaseDate;

    private String description;

    @Override
    public String toString() {
        return "MovieDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + releaseDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
