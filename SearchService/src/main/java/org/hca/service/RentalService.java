package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.domain.CarResponseDto;
import org.hca.domain.Rental;
import org.hca.repository.RentalRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    @RabbitListener(queues = "q.rental.save")
    public void save(Rental rental) {
        rentalRepository.save(rental);
    }
}
