package org.hca.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hca.dto.response.CarResponseDto;
import org.hca.entity.Office;
import org.hca.mapper.CustomCarMapper;
import org.hca.service.CarService;
import org.hca.service.OfficeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticDataSender {
	private final CarService carService;
	private final OfficeService officeService;
	private final RabbitTemplate rabbitTemplate;

	//@PostConstruct
	public void sendCars(){
		List<CarResponseDto> inventoryOutput = carService.inventoryOutput();
		inventoryOutput.forEach(car ->{
			rabbitTemplate.convertAndSend("exchange.direct.carSave","Routing.CarSave",car);
		});
	}

	//@PostConstruct
	public void sendOffices(){
		List<Office> inventoryOutput = officeService.findAll();
		inventoryOutput.forEach(office ->{
			rabbitTemplate.convertAndSend("exchange.direct.officeSave", "Routing.OfficeSave", office);
		});
	}
}