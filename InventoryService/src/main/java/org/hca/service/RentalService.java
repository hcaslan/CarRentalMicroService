package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.RentalSaveRequest;
import org.hca.entity.Rental;
import org.hca.repository.RentalRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final RabbitTemplate rabbitTemplate;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Rental save(RentalSaveRequest rentalSaveRequest) {
        Rental rental = new Rental();
        rental.setProfileId(rentalSaveRequest.profileId());
        rental.setCarId(rentalSaveRequest.carId());
        rental.setStartDate(rentalSaveRequest.startDate());
        rental.setEndDate(rentalSaveRequest.endDate());
        rental.setPickupOffice(rentalSaveRequest.pickupOffice());
        rental.setDropoffOffice(rentalSaveRequest.dropoffOffice());
        rabbitTemplate.convertAndSend("exchange.direct.rentalSave","Routing.RentalSave",rental);
        return rentalRepository.save(rental);
    }
}
