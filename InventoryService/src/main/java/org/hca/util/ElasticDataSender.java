package org.hca.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hca.dto.CarRabbitMqDto;
import org.hca.entity.Brand;
import org.hca.entity.Car;
import org.hca.entity.Model;
import org.hca.mapper.CustomCarMapper;
import org.hca.repository.CarRepository;
import org.hca.service.BrandService;
import org.hca.service.CarService;
import org.hca.service.ModelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticDataSender {
	private final CarService carService;
	private final CustomCarMapper customCarMapper;
	private final RabbitTemplate rabbitTemplate;

	//@PostConstruct
	public void send(){
		List<Car> allUserProfiles = carService.findAll();
		allUserProfiles.forEach(car ->{
			CarRabbitMqDto carRabbitMqDto = customCarMapper.carToRabbitMqDto(car);
			rabbitTemplate.convertAndSend("exchange.direct.carSave","Routing.CarSave",carRabbitMqDto);
		});
	}
}