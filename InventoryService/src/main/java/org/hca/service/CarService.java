package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.CarSaveRequest;
import org.hca.dto.request.CarUpdateRequest;
import org.hca.dto.response.CarResponseDto;
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

import java.util.ArrayList;
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
        Car car = customCarMapper.carSaveRequestToCar(carSaveRequest, fuelType, gearType, category);
        carRepository.save(car);
        CarResponseDto carResponseDto = customCarMapper.carToCarResponseDto(car);
        rabbitTemplate.convertAndSend("exchange.direct.carSave", "Routing.CarSave", carResponseDto);
        return car;
    }

    public Car update(CarUpdateRequest carUpdateRequest, FuelType fuelType, GearType gearType, Category category) {
        Optional<Car> optionalCar = customCarMapper.carUpdateRequestToCar(carUpdateRequest, fuelType, gearType, category);
        if(optionalCar.isPresent()) {
            Car car = optionalCar.get();
            CarResponseDto carResponseDto = customCarMapper.carToCarResponseDto(car);
            rabbitTemplate.convertAndSend("exchange.direct.carSave", "Routing.CarSave", carResponseDto);
            carRepository.save(car);
        }
        return optionalCar.orElseThrow(()-> new InventoryServiceException(ErrorType.CAR_NOT_FOUND));
    }

    public Car delete(String carId) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isPresent()) {
            car.get().setDeleted(true);
            CarResponseDto carResponseDto = customCarMapper.carToCarResponseDto(car.get());
            rabbitTemplate.convertAndSend("exchange.direct.carSave", "Routing.CarSave", carResponseDto);
            return carRepository.save(car.get());
        } else {
            throw new InventoryServiceException(ErrorType.CAR_NOT_FOUND);
        }
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<CarResponseDto> inventoryOutput() {
        List<CarResponseDto> inventoryOutput = new ArrayList<>();
        carRepository.findAll().forEach(car -> {
            inventoryOutput.add(customCarMapper.carToCarResponseDto(car));
        });
        return inventoryOutput;
    }

    public Car findById(String carId) {
        return carRepository.findById(carId).orElseThrow(() -> new InventoryServiceException(ErrorType.CAR_NOT_FOUND));
    }
}
