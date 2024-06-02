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
@RequestMapping(ROOT + SEARCH + CARS)
@RequiredArgsConstructor
@CrossOrigin
public class CarController {
    private final CarService carService;

    @GetMapping(FIND_ALL)
    public ResponseEntity<Page<CarResponseDto>> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(carService.findAll(page, size));
    }
    @GetMapping(FILTER)
    public ResponseEntity<Page<CarResponseDto>> filter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String gearType,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String minDaily,
            @RequestParam(required = false) String maxDaily) {

        return ResponseEntity.ok(carService.filter(page, size, category, gearType, fuelType, minDaily, maxDaily));
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
