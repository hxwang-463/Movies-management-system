package xyz.hxwang.su.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.hxwang.su.dao.ModelRepository;
import xyz.hxwang.su.dao.MovieRepository;
import xyz.hxwang.su.entity.Model;
import xyz.hxwang.su.entity.Movie;
import xyz.hxwang.su.exceptions.ResourcesNotFoundException;
import xyz.hxwang.su.payload.*;
import xyz.hxwang.su.service.ModelService;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final MovieRepository movieRepository;

    public ModelServiceImpl(ModelMapper modelMapper, ModelRepository modelRepository, MovieRepository movieRepository) {
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ModelDto createModel(ModelDto modelDto) {
        Model model = modelMapper.map(modelDto, Model.class);
        model = modelRepository.save(model);
        return modelMapper.map(model, ModelDto.class);
    }

    @Override
    public List<ModelDto> getAllModels() {
        List<Model> models = modelRepository.findAll();
        return models.stream().map(model -> modelMapper.map(model, ModelDto.class)).toList();
    }

    @Override
    public ModelPage getAllModels(int pageNo, int pageSize, String sortBy, String sortDir, String name) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Model> page = null;

        if(name.isEmpty())
            page = modelRepository.findAll(pageRequest);
        else
            page = modelRepository.findModelsByNameContainingIgnoreCase(name, pageRequest);

        return ModelPage.builder()
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .content(page.getContent().stream().map(model -> modelMapper.map(model, ModelDto.class)).toList())
                .build();
    }

    @Override
    public ModelWithMovieDto getModelById(Long id, int pageNo, int pageSize, String sortBy, String sortDir) {
        Model model = modelRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Model", "id", id));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Movie> movies = movieRepository.findMoviesByModelsIs(model, pageRequest);

        MoviePage moviePage = MoviePage.builder()
                .pageNo(movies.getNumber())
                .pageSize(movies.getSize())
                .totalElements(movies.getTotalElements())
                .totalPages(movies.getTotalPages())
                .last(movies.isLast())
                .content(movies.getContent().stream().map(movie->modelMapper.map(movie, MovieDto.class)).toList())
                .build();

        ModelWithMovieDto dto = ModelWithMovieDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .movies(moviePage)
                .build();
        return dto;

    }

    @Override
    public ModelDto updateModel(Long id, ModelDto modelDto) {
        Model model = modelRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Model", "id", id));
        model.setName(modelDto.getName());
        model.setDescription(modelDto.getDescription());
        model = modelRepository.save(model);
        return modelMapper.map(model, ModelDto.class);
    }

    @Override
    public void deleteModel(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(()->new ResourcesNotFoundException("Model", "id", id));
        modelRepository.deleteById(id);
    }
}
