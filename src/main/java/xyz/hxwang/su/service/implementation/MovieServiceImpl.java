package xyz.hxwang.su.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.hxwang.su.dao.ModelRepository;
import xyz.hxwang.su.dao.MovieRepository;
import xyz.hxwang.su.dao.SeriesRepository;
import xyz.hxwang.su.entity.Model;
import xyz.hxwang.su.entity.Movie;
import xyz.hxwang.su.exceptions.ResourcesNotFoundException;
import xyz.hxwang.su.payload.*;
import xyz.hxwang.su.service.MovieService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final ModelRepository modelRepository;

    public MovieServiceImpl(ModelMapper modelMapper, MovieRepository movieRepository, SeriesRepository seriesRepository, ModelRepository modelRepository) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public MovieWithSeriesDto createMovie(MovieWithSeriesDto modelDto) {
        Movie model = mapToEntity(modelDto);
        model = movieRepository.save(model);
        return mapToDto(model);
    }

    private Movie mapToEntity(MovieWithSeriesDto movieDto){
        Movie movie = Movie.builder()
                .id(movieDto.getId())
                .name(movieDto.getName())
                .releaseDate(movieDto.getReleaseDate())
                .description(movieDto.getDescription())
                .series(seriesRepository.findById(movieDto.getSeries()).orElseThrow(()->new ResourcesNotFoundException("Series", "id", movieDto.getSeries())))
                .build();
        return movie;

    }

//    @Override
//    public List<MovieWithSeriesDto> getAllMovies() {
//        List<Movie> models = movieRepository.findAll();
//        return models.stream().map(model -> modelMapper.map(model, MovieWithSeriesDto.class)).toList();
//    }

    @Override
    public MoviePageWithSeries getAllMovies(int pageNo, int pageSize, String sortBy, String sortDir, long series) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> page = null;
        if(series==0)
            page = movieRepository.findAll(pageRequest);
        else
            page = movieRepository.findMoviesBySeriesIs(seriesRepository.findById(series).orElseThrow(()->new ResourcesNotFoundException("Series", "id", series)), pageRequest);


        MoviePageWithSeries moviePage = MoviePageWithSeries.builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .content(page.getContent().stream().map(movie -> mapToDto(movie)).toList())
                .build();


        return moviePage;
    }

    private MovieWithSeriesDto mapToDto(Movie movie){
        MovieWithSeriesDto movieDto = MovieWithSeriesDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .releaseDate(movie.getReleaseDate())
                .description(movie.getDescription())
                .series(movie.getSeries().getId())
                .build();
        return movieDto;
    }

    @Override
    public MovieWithModelDto getMovieById(Long movieId) {
        return movieRepository.findById(movieId).map(movie -> {
            MovieWithSeriesDto dto = mapToDto(movie);
            Set<ModelDto> modelDTOs = movie.getModels().stream()
                    .map(model -> modelMapper.map(model, ModelDto.class))
                    .collect(Collectors.toSet());
            return MovieWithModelDto.builder()
                    .models(modelDTOs).series(dto.getSeries())
                    .id(dto.getId()).name(dto.getName())
                    .releaseDate(dto.getReleaseDate()).description(dto.getDescription()).build();
        }).orElseThrow(()->new ResourcesNotFoundException("Movie", "id", movieId));
    }

    @Override
    public MovieWithSeriesDto updateMovie(Long id, MovieWithSeriesDto modelDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Movie", "id", id));
        movie.setName(modelDto.getName());
        movie.setDescription(modelDto.getDescription());
        movie = movieRepository.save(movie);
        return mapToDto(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Movie", "id", id));
        movieRepository.deleteById(id);
    }

    @Override
    public void addModelToMovie(Long movieId, Long modelId) {
        Model model = modelRepository.findById(modelId).orElseThrow(()->new ResourcesNotFoundException("Model", "id", modelId));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ResourcesNotFoundException("Movie", "id", movieId));

        movie.addModel(model);
        movieRepository.save(movie);
    }

    @Override
    public void deleteModelFromMovie(Long movieId, Long modelId) {
        Model model = modelRepository.findById(modelId).orElseThrow(()->new ResourcesNotFoundException("Model", "id", modelId));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new ResourcesNotFoundException("Movie", "id", movieId));

        movie.removeModel(model);
        movieRepository.save(movie);

    }


}
