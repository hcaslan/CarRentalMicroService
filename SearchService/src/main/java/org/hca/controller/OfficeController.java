package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.domain.Office;
import org.hca.service.OfficeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + SEARCH + OFFICES)
@RequiredArgsConstructor
@CrossOrigin
public class OfficeController {
    private final OfficeService officeService;

    @GetMapping(FILTER)
    public ResponseEntity<Page<Office>> filter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String officeType) {
        System.out.println(city);
        System.out.println(officeType);
        return ResponseEntity.ok(officeService.filter(page, size, city, officeType));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<Page<Office>> findAll() {
        return ResponseEntity.ok(officeService.findAll());
    }
}
