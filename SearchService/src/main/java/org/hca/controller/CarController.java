package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.domain.Car;
import org.hca.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @GetMapping("/fuzzyFindByName")
    public ResponseEntity<List<Car>> fuzzyFindByName(@RequestParam String name) {
        return ResponseEntity.ok(carService.fuzzyFindByName(name));
    }
    @GetMapping("/rangeByPrice")
    public ResponseEntity<List<Car>> rangeByPrice(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return ResponseEntity.ok(carService.rangeByPrice(minPrice, maxPrice));
    }
}
