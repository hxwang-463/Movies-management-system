package xyz.hxwang.su.service;

import xyz.hxwang.su.payload.ModelDto;
import xyz.hxwang.su.payload.ModelPage;
import xyz.hxwang.su.payload.ModelWithMovieDto;

import java.util.List;

public interface ModelService {
    ModelDto createModel(ModelDto modelDto);
    List<ModelDto> getAllModels();
    ModelPage getAllModels(int pageNo, int pageSize, String sortBy, String sortDir, String name);
    ModelWithMovieDto getModelById(Long id, int pageNo, int pageSize, String sortBy, String sortDir);
    ModelDto updateModel(Long id, ModelDto modelDto);
    void deleteModel(Long id);

}
