package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.OfficeSaveRequest;
import org.hca.dto.request.OfficeUpdateRequest;
import org.hca.dto.response.CarResponseDto;
import org.hca.entity.Office;
import org.hca.entity.enums.OfficeType;
import org.hca.exception.ErrorType;
import org.hca.exception.InventoryServiceException;
import org.hca.mapper.OfficeMapper;
import org.hca.repository.OfficeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;
    private final RabbitTemplate rabbitTemplate;

    public Office save(OfficeSaveRequest officeSaveRequest, OfficeType officeType) {
        officeSaveRequest.setOfficeType(officeType);
        Office office = officeMapper.dtoToOffice(officeSaveRequest);
        rabbitTemplate.convertAndSend("exchange.direct.officeSave", "Routing.OfficeSave", office);
        return officeRepository.save(office);
    }

    public Office update(OfficeUpdateRequest officeUpdateRequest, OfficeType officeType) {
        Optional<Office> optionalOffice = officeRepository.findById(officeUpdateRequest.getId());
        officeUpdateRequest.setOfficeType(officeType);
        Office office = officeMapper.dtoToOffice(officeUpdateRequest);
        office.setCreatedAt(optionalOffice.orElseThrow(()-> new InventoryServiceException(ErrorType.OFFICE_NOT_FOUND)).getCreatedAt());
        rabbitTemplate.convertAndSend("exchange.direct.officeSave", "Routing.OfficeSave", office);
        return officeRepository.save(office);
    }

    public Office delete(String officeId) {
        Optional<Office> office = officeRepository.findById(officeId);
        if (office.isPresent()) {
            office.get().setDeleted(true);
            rabbitTemplate.convertAndSend("exchange.direct.officeSave", "Routing.OfficeSave", office.get());
            return officeRepository.save(office.get());
        }else {
            throw new InventoryServiceException(ErrorType.OFFICE_NOT_FOUND);
        }
    }
    public List<Office> findAll() {
        return officeRepository.findAll();
    }

    public Office findById(String modelId) {
        return officeRepository.findById(modelId).orElseThrow(()-> new InventoryServiceException(ErrorType.OFFICE_NOT_FOUND));
    }
}
