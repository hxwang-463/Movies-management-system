package xyz.hxwang.su.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.hxwang.su.payload.ModelDto;
import xyz.hxwang.su.payload.ModelPage;
import xyz.hxwang.su.service.ModelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/models")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<ModelDto> createModel(@Valid @RequestBody ModelDto modelDto){
        ModelDto response = modelService.createModel(modelDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ModelPage> getAllModels(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @RequestParam(value = "name", defaultValue = "", required = false) String name
    ){
        return new ResponseEntity<>(modelService.getAllModels(pageNo, pageSize, sortBy, sortDir, name), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModelById(
            @PathVariable(name="id") long id,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return new ResponseEntity<>(modelService.getModelById(id, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelDto> updateModelById(@PathVariable(name="id") long id, @Valid @RequestBody ModelDto modelDto){
        return new ResponseEntity<>(modelService.updateModel(id, modelDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModelById(@PathVariable(name="id") long id){
        modelService.deleteModel(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
