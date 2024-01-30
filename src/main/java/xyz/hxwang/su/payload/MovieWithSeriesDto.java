package xyz.hxwang.su.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieWithSeriesDto {
    private Long id;

    private String name;

    private Date releaseDate;

    private Long series;

    private String description;
}
