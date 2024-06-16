package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.OfficeSaveRequest;
import org.hca.dto.request.OfficeUpdateRequest;
import org.hca.dto.request.RentalSaveRequest;
import org.hca.entity.Office;
import org.hca.entity.Rental;
import org.hca.entity.enums.OfficeType;
import org.hca.service.OfficeService;
import org.hca.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROOT+INVENTORY+RENTAL)
public class RentalController {
    private final RentalService rentalService;
    @PostMapping(SAVE)
    public ResponseEntity<Rental> save(@RequestBody RentalSaveRequest rentalSaveRequest) {
        return ResponseEntity.ok(rentalService.save(rentalSaveRequest));
    }
//    @PutMapping(UPDATE)
//    public ResponseEntity<Office> update(@RequestBody OfficeUpdateRequest officeUpdateRequest, @RequestParam OfficeType officeType) {
//        return ResponseEntity.ok(rentalService.update(officeUpdateRequest, officeType));
//    }
//    @DeleteMapping(DELETE)
//    public ResponseEntity<Office> delete(@RequestParam String officeId) {
//        return ResponseEntity.ok(rentalService.delete(officeId));
//    }
//    @GetMapping(FIND_ALL)
//    public ResponseEntity<List<Office>> findAll() {
//        return ResponseEntity.ok(officeService.findAll());
//    }
//    @GetMapping(FIND_BY_ID)
//    public ResponseEntity<Office> findById(@RequestParam String officeId) {
//        return ResponseEntity.ok(rentalService.findById(officeId));
//    }
}
