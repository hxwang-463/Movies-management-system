package xyz.hxwang.su.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesWithMovieDto {
    private Long id;
    @NotEmpty
    private String name;

    private String description;

    private MoviePage movies;
}
