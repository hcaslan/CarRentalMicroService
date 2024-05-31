package org.hca.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.CarSaveRequest;
import org.hca.dto.request.CarUpdateRequest;
import org.hca.dto.response.CarResponseDto;
import org.hca.entity.Brand;
import org.hca.entity.Car;
import org.hca.entity.Model;
import org.hca.entity.enums.Category;
import org.hca.entity.enums.FuelType;
import org.hca.entity.enums.GearType;
import org.hca.repository.CarRepository;
import org.hca.service.BrandService;
import org.hca.service.ModelService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomCarMapper {
    private final ModelService modelService;
    private final BrandService brandService;
    private final CarRepository carRepository;
    public Car carSaveRequestToCar(CarSaveRequest carSaveRequest, FuelType fuelType, GearType gearType, Category category){
        Model model = modelService.findById(carSaveRequest.modelId());
        Brand brand = brandService.findById(model.getBrandId());
        return (Car) Car.builder()
                .modelId(carSaveRequest.modelId())
                .category(category)
                .fuelType(fuelType)
                .gearType(gearType)
                .plate(carSaveRequest.plate())
                .dailyPrice(carSaveRequest.dailyPrice())
                .modelYear(carSaveRequest.modelYear())
                .image(carSaveRequest.image())
                .seats(carSaveRequest.seats())
                .name(brand.getName() + " " + model.getName())
                .build();
    }
    public Car carUpdateRequestToCar(CarUpdateRequest carUpdateRequest, FuelType fuelType, GearType gearType, Category category){
        Model model = modelService.findById(carUpdateRequest.modelId());
        Brand brand = brandService.findById(model.getBrandId());
        LocalDateTime createdAt = carRepository.findById(carUpdateRequest.id()).get().getCreatedAt();
        return (Car) Car.builder()
                .category(category)
                .fuelType(fuelType)
                .gearType(gearType)
                .plate(carUpdateRequest.plate())
                .dailyPrice(carUpdateRequest.dailyPrice())
                .modelYear(carUpdateRequest.modelYear())
                .image(carUpdateRequest.image())
                .seats(carUpdateRequest.seats())
                .createdAt(createdAt)
                .name(brand.getName() + " " + model.getName())
                .id(carUpdateRequest.id())
                .build();
    }
    public CarResponseDto carToCarResponseDto(Car car){
        Model model = modelService.findById(car.getModelId());
        assert model != null;
        Brand brand = brandService.findById(model.getBrandId());
        assert brand != null;
        return CarResponseDto.builder()
                .id(car.getId())
                .name(car.getName())
                .brand(brand.getName())
                .model(model.getName())
                .category(car.getCategory())
                .fuelType(car.getFuelType())
                .gearType(car.getGearType())
                .image(car.getImage())
                .modelYear(car.getModelYear())
                .dailyPrice(car.getDailyPrice())
                .seats(car.getSeats())
                .plate(car.getPlate())
                .status(car.getStatus())
                .build();
    }
}
