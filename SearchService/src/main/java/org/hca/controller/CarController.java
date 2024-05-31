package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.domain.CarResponseDto;
import org.hca.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + SEARCH)
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(FIND_ALL)
    @CrossOrigin(origins = "http://localhost:3001")
    public ResponseEntity<Page<CarResponseDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        System.out.println("SearchService.findAll");
        System.out.println("page: " + page);
        System.out.println("size: " + size);
        return ResponseEntity.ok(carService.findAll(page, size));
    }

    @GetMapping("/fuzzyFindByName")
    public ResponseEntity<List<CarResponseDto>> fuzzyFindByName(@RequestParam String name) {
        return ResponseEntity.ok(carService.fuzzyFindByName(name));
    }

    @GetMapping("/rangeByPrice")
    public ResponseEntity<List<CarResponseDto>> rangeByPrice(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return ResponseEntity.ok(carService.rangeByPrice(minPrice, maxPrice));
    }
}
