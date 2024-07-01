package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.domain.Office;
import org.hca.domain.Rental;
import org.hca.service.RentalService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + SEARCH + RENTAL)
@RequiredArgsConstructor
@CrossOrigin
public class RentalController {
    private final RentalService rentalService;
    @GetMapping(FIND_ALL)
    public ResponseEntity<Page<Rental>> findAll() {
        return ResponseEntity.ok(rentalService.findAll());
    }
    @GetMapping("/")
    public Page<Rental> findByTime(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return rentalService.findRentalsInATimeInterval(startDate, endDate);
    }

    @GetMapping("/newTime")
    public List<String> findRentedCars(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return rentalService.findRentedCars(startDate, endDate);
    }
}
