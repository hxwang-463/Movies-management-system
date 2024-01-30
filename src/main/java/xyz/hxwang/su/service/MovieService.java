package xyz.hxwang.su.service;

import xyz.hxwang.su.payload.MoviePageWithSeries;
import xyz.hxwang.su.payload.MovieWithModelDto;
import xyz.hxwang.su.payload.MovieWithSeriesDto;

public interface MovieService {
    MovieWithSeriesDto createMovie(MovieWithSeriesDto movieDto);
//    List<MovieDto> getAllMovies();
    MoviePageWithSeries getAllMovies(int pageNo, int pageSize, String sortBy, String sortDir, long series);
    MovieWithModelDto getMovieById(Long id);
    MovieWithSeriesDto updateMovie(Long id, MovieWithSeriesDto movieDto);
    void deleteMovie(Long id);

    void addModelToMovie(Long movieId, Long modelId);

    void deleteModelFromMovie(Long movieId, Long modelId);
}
