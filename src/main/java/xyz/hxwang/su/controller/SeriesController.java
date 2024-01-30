package xyz.hxwang.su.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.hxwang.su.payload.ModelPage;
import xyz.hxwang.su.payload.SeriesDto;
import xyz.hxwang.su.service.SeriesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping
    public ResponseEntity<SeriesDto> createSeries(@Valid @RequestBody SeriesDto seriesDto) {
        SeriesDto response = seriesService.createSeries(seriesDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SeriesDto>> getAllSeries(){
        return new ResponseEntity<>(seriesService.getAllSeries(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeriesById(
            @PathVariable(name="id") long id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return new ResponseEntity<>(seriesService.getSeriesById(id, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeriesDto> updateSeriesById(@PathVariable(name="id") long id, @Valid @RequestBody SeriesDto seriesDto){
        return new ResponseEntity<>(seriesService.updateSeries(id, seriesDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeriesById(@PathVariable(name="id") long id){
        seriesService.deleteSeries(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
