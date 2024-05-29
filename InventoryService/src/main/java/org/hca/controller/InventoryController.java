package org.hca.controller;

import lombok.RequiredArgsConstructor;
import org.hca.entity.Car;
import org.hca.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InventoryController {
    private final CarService carService;
    @GetMapping("/cars")
    public String getAllCars(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        System.out.println("Cars: " + cars);
        return "carList";
    }
}
