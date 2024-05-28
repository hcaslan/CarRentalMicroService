package org.hca.mapper;

import lombok.RequiredArgsConstructor;
import org.hca.dto.CarRabbitMqDto;
import org.hca.entity.Brand;
import org.hca.entity.Car;
import org.hca.entity.Model;
import org.hca.service.BrandService;
import org.hca.service.ModelService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomCarMapper {
    private final ModelService modelService;
    private final BrandService brandService;
    public CarRabbitMqDto carToRabbitMqDto (Car car){
        Model model = modelService.findById(car.getModelId());
        Brand brand = brandService.findById(model.getBrandId());
        CarRabbitMqDto dto=new CarRabbitMqDto(
                car.getId(),
                model.getName(),
                brand.getName(),
                car.getCategory(),
                car.getFuelType(),
                car.getGearType(),
                car.getStatus(),
                car.isDeleted(),
                car.getModelYear(),
                car.getPlate(),
                car.getDailyPrice()
        );
        return dto;
    }
}
