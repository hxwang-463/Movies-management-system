package xyz.hxwang.su.service;

import xyz.hxwang.su.payload.SeriesDto;
import xyz.hxwang.su.payload.SeriesWithMovieDto;


import java.util.List;

public interface SeriesService {
    SeriesDto createSeries(SeriesDto seriesDto);
    List<SeriesDto> getAllSeries();
    SeriesWithMovieDto getSeriesById(Long id, int pageNo, int pageSize, String sortBy, String sortDir);
    SeriesDto updateSeries(Long id, SeriesDto seriesDto);
    void deleteSeries(Long id);
}
