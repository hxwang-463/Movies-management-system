package xyz.hxwang.su.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hxwang.su.entity.Model;
import xyz.hxwang.su.entity.Movie;
import xyz.hxwang.su.entity.Series;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findMoviesBySeriesIs(Series series, Pageable pageable);

    Page<Movie> findMoviesByModelsIs(Model model, Pageable pageable);
}
