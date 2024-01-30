package xyz.hxwang.su.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.hxwang.su.payload.ModelDto;
import xyz.hxwang.su.payload.MoviePageWithSeries;
import xyz.hxwang.su.payload.MovieWithSeriesDto;
import xyz.hxwang.su.service.MovieService;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieWithSeriesDto movieDto) {
        MovieWithSeriesDto response = movieService.createMovie(movieDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MoviePageWithSeries> getAllMovies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @RequestParam(value = "series", defaultValue = "0", required = false) long series
    ){
        return new ResponseEntity<>(movieService.getAllMovies(pageNo, pageSize, sortBy, sortDir, series), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable(name="id") long id){
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovieById(@PathVariable(name="id") long id, @Valid @RequestBody MovieWithSeriesDto movieDto){
        return new ResponseEntity<>(movieService.updateMovie(id, movieDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable(name="id") long id){
        movieService.deleteMovie(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/{movieId}/model")
    public ResponseEntity<?> addModelToMovie(@PathVariable Long movieId, @RequestBody ModelDto modelDto) {

        movieService.addModelToMovie(movieId, modelDto.getId());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/model/{modelId}")
    public ResponseEntity<?> deleteModelFromMovie(@PathVariable Long movieId, @PathVariable Long modelId) {

        movieService.deleteModelFromMovie(movieId, modelId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
