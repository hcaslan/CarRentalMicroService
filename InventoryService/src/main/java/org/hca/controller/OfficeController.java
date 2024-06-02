package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ModelSaveRequest;
import org.hca.dto.request.ModelUpdateRequest;
import org.hca.dto.request.OfficeSaveRequest;
import org.hca.dto.request.OfficeUpdateRequest;
import org.hca.entity.Model;
import org.hca.entity.Office;
import org.hca.entity.enums.OfficeType;
import org.hca.service.ModelService;
import org.hca.service.OfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROOT+INVENTORY+OFFICE)
public class OfficeController {
    private final OfficeService officeService;
    @PostMapping(SAVE)
    public ResponseEntity<Office> save(@RequestBody OfficeSaveRequest officeSaveRequest, @RequestParam OfficeType officeType) {
        return ResponseEntity.ok(officeService.save(officeSaveRequest, officeType));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Office> update(@RequestBody OfficeUpdateRequest officeUpdateRequest, @RequestParam OfficeType officeType) {
        return ResponseEntity.ok(officeService.update(officeUpdateRequest, officeType));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Office> delete(@RequestParam String officeId) {
        return ResponseEntity.ok(officeService.delete(officeId));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Office>> findAll() {
        return ResponseEntity.ok(officeService.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Office> findById(@RequestParam String officeId) {
        return ResponseEntity.ok(officeService.findById(officeId));
    }
}
