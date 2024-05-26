package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.BrandSaveRequest;
import org.hca.dto.request.BrandUpdateRequest;
import org.hca.entity.Brand;
import org.hca.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROOT+INVENTORY+BRAND)
public class BrandController {
    private final BrandService brandService;
    @PostMapping(SAVE)
    public ResponseEntity<Brand> save(@RequestBody BrandSaveRequest brandSaveRequest) {
        return ResponseEntity.ok(brandService.save(brandSaveRequest));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Brand> update(@RequestBody BrandUpdateRequest brandUpdateRequest) {
        return ResponseEntity.ok(brandService.update(brandUpdateRequest));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Brand> delete(@RequestParam String brandId) {
        return ResponseEntity.ok(brandService.delete(brandId));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Brand>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Brand> findById(@RequestParam String brandId) {
        return ResponseEntity.ok(brandService.findById(brandId));
    }
}
