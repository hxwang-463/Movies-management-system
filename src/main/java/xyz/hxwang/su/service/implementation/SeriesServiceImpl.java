package xyz.hxwang.su.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.hxwang.su.dao.MovieRepository;
import xyz.hxwang.su.dao.SeriesRepository;
import xyz.hxwang.su.entity.Movie;
import xyz.hxwang.su.entity.Series;
import xyz.hxwang.su.exceptions.ResourcesNotFoundException;
import xyz.hxwang.su.payload.*;
import xyz.hxwang.su.payload.SeriesDto;
import xyz.hxwang.su.service.SeriesService;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final ModelMapper modelMapper;
    private final SeriesRepository seriesRepository;
    private final MovieRepository movieRepository;

    public SeriesServiceImpl(ModelMapper modelMapper, SeriesRepository seriesRepository, MovieRepository movieRepository) {
        this.modelMapper = modelMapper;
        this.seriesRepository = seriesRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public SeriesDto createSeries(SeriesDto seriesDto) {
        Series series = modelMapper.map(seriesDto, Series.class);
        series = seriesRepository.save(series);
        return modelMapper.map(series, SeriesDto.class);
    }

    @Override
    public List<SeriesDto> getAllSeries() {
        List<Series> models = seriesRepository.findAll();
        return models.stream().map(series -> modelMapper.map(series, SeriesDto.class)).toList();
    }

    @Override
    public SeriesWithMovieDto getSeriesById(Long id, int pageNo, int pageSize, String sortBy, String sortDir) {
        Series series = seriesRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Series", "id", id));
        SeriesWithMovieDto seriesDto = modelMapper.map(series, SeriesWithMovieDto.class);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> page = movieRepository.findMoviesBySeriesIs(seriesRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Series", "id", id)), pageRequest);
        MoviePage moviePage = MoviePage.builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .content(page.getContent().stream().map(movie->modelMapper.map(movie, MovieDto.class)).toList())
                .build();

        seriesDto.setMovies(moviePage);
        return seriesDto;
    }

    @Override
    public SeriesDto updateSeries(Long id, SeriesDto seriesDto) {
        Series series = seriesRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Series", "id", id));
        series.setName(seriesDto.getName());
        series.setDescription(seriesDto.getDescription());
        series = seriesRepository.save(series);
        return modelMapper.map(series, SeriesDto.class);
    }

    @Override
    public void deleteSeries(Long id) {
        seriesRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Series", "id", id));
        seriesRepository.deleteById(id);
    }
}
