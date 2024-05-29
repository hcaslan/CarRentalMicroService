package org.hca.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hca.dto.CarRabbitMqDto;
import org.hca.entity.Car;
import org.hca.mapper.CustomCarMapper;
import org.hca.service.CarService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticDataSender {
	private final CarService carService;
	private final CustomCarMapper customCarMapper;
	private final RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void send(){
		List<Car> allUserProfiles = carService.findAll();
		allUserProfiles.forEach(car ->{
			rabbitTemplate.convertAndSend("exchange.direct.carSave","Routing.CarSave",car);
		});
	}
}