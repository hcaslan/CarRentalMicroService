package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ModelSaveRequest;
import org.hca.dto.request.ModelUpdateRequest;
import org.hca.entity.Model;
import org.hca.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROOT+INVENTORY+MODEL)
public class ModelController {
    private final ModelService modelService;
    @PostMapping(SAVE)
    public ResponseEntity<Model> save(@RequestBody ModelSaveRequest modelSaveRequest) {
        return ResponseEntity.ok(modelService.save(modelSaveRequest));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Model> update(@RequestBody ModelUpdateRequest modelUpdateRequest) {
        return ResponseEntity.ok(modelService.update(modelUpdateRequest));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Model> delete(@RequestParam String modelId) {
        return ResponseEntity.ok(modelService.delete(modelId));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Model>> findAll() {
        return ResponseEntity.ok(modelService.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Model> findById(@RequestParam String modelId) {
        return ResponseEntity.ok(modelService.findById(modelId));
    }
}
