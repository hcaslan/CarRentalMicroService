package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.CarRabbitMqDto;
import org.hca.dto.request.CarSaveRequest;
import org.hca.dto.request.CarUpdateRequest;
import org.hca.entity.Car;
import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.exception.ErrorType;
import org.hca.exception.InventoryServiceException;
import org.hca.mapper.CarMapper;
import org.hca.mapper.CustomCarMapper;
import org.hca.repository.CarRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CustomCarMapper customCarMapper;
    private final RabbitTemplate rabbitTemplate;

    public Car save(CarSaveRequest carSaveRequest, FuelType fuelType, GearType gearType, Category category) {
        Car car = carMapper.dtoToCar(carSaveRequest);
        car.setFuelType(fuelType);
        car.setGearType(gearType);
        car.setCategory(category);
        CarRabbitMqDto carRabbitMqDto = customCarMapper.carToRabbitMqDto(car);
        rabbitTemplate.convertAndSend("exchange.direct.carSave","Routing.CarSave",carRabbitMqDto);
        return carRepository.save(car);
    }

    public Car update(CarUpdateRequest carUpdateRequest, FuelType fuelType, GearType gearType, Category category) {
        Car car = carMapper.dtoToCar(carUpdateRequest);
        car.setFuelType(fuelType);
        car.setGearType(gearType);
        car.setCategory(category);
        return carRepository.save(car);
    }

    public Car delete(String carId) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isPresent()) {
            car.get().setDeleted(true);
            return carRepository.save(car.get());
        }else {
            throw new InventoryServiceException(ErrorType.CAR_NOT_FOUND);
        }
    }
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(String carId) {
        return carRepository.findById(carId).orElseThrow(()-> new InventoryServiceException(ErrorType.CAR_NOT_FOUND));
    }
}
