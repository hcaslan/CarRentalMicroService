package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.CarSaveRequest;
import org.hca.dto.request.CarUpdateRequest;
import org.hca.entity.Car;
import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ROOT+INVENTORY+CAR)
public class CarController {
    private final CarService carService;
    @PostMapping(SAVE)
    public ResponseEntity<Car> save(@RequestParam FuelType fuelType, @RequestParam GearType gearType, @RequestParam Category category, @RequestBody CarSaveRequest carSaveRequest) {
        return ResponseEntity.ok(carService.save(carSaveRequest, fuelType, gearType, category));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Car> update(@RequestParam FuelType fuelType, @RequestParam GearType gearType, @RequestParam Category category, @RequestBody CarUpdateRequest carUpdateRequest) {
        return ResponseEntity.ok(carService.update(carUpdateRequest, fuelType, gearType, category));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<Car> delete(@RequestParam String carId) {
        return ResponseEntity.ok(carService.delete(carId));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Car>> findAll() {
        System.out.println("CarService.findAll");
        return ResponseEntity.ok(carService.findAll());
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Car> findById(@RequestParam String carId) {
        return ResponseEntity.ok(carService.findById(carId));
    }
}
