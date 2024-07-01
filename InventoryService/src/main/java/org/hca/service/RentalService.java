package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.RentalSaveRequest;
import org.hca.entity.Office;
import org.hca.entity.Rental;
import org.hca.repository.RentalRepository;
import org.hca.util.JwtUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final OfficeService officeService;
    private final RabbitTemplate rabbitTemplate;
    private final JwtUtil jwtUtil;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
    @Transactional
    public Rental save(RentalSaveRequest rentalSaveRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if(jwtUtil.isTokenExpired(rentalSaveRequest.token())){
            throw new RuntimeException("Token expired");
        }
        String email = jwtUtil.extractUsername(rentalSaveRequest.token());
        System.out.println(email);
        if(email == null){
            throw new RuntimeException("Invalid token");
        }
        String profileId = (String)rabbitTemplate.convertSendAndReceive("exchange.direct.getProfileId","Routing.GetProfileId",email);
        Rental rental = new Rental();
        rental.setProfileId(profileId);
        System.out.println(profileId);
        rental.setCarId(rentalSaveRequest.carId());
        rental.setStartDate(LocalDateTime.parse(rentalSaveRequest.startDate(),formatter));
        rental.setEndDate(LocalDateTime.parse(rentalSaveRequest.endDate(),formatter));
        rental.setPickupOffice(officeService.findByName(rentalSaveRequest.pickupOffice()));
        rental.setDropoffOffice(officeService.findByName(rentalSaveRequest.dropoffOffice()));
        rabbitTemplate.convertAndSend("exchange.direct.rentalSave","Routing.RentalSave",rental);
        return rentalRepository.save(rental);
    }
}
