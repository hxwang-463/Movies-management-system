package xyz.hxwang.su.payload;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieWithModelDto {
    private Long id;

    private String name;

    private Date releaseDate;

    private Long series;

    private String description;

    private Set<ModelDto> models;
}
