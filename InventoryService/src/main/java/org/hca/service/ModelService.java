package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ModelSaveRequest;
import org.hca.dto.request.ModelUpdateRequest;
import org.hca.entity.Model;
import org.hca.exception.ErrorType;
import org.hca.exception.InventoryServiceException;
import org.hca.mapper.ModelMapper;
import org.hca.repository.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public Model save(ModelSaveRequest modelSaveRequest) {
        Model model = modelMapper.dtoToModel(modelSaveRequest);
        return modelRepository.save(model);
    }

    public Model update(ModelUpdateRequest modelUpdateRequest) {
        Model model = modelMapper.dtoToModel(modelUpdateRequest);
        return modelRepository.save(model);
    }

    public Model delete(String modelId) {
        Optional<Model> model = modelRepository.findById(modelId);
        if (model.isPresent()) {
            model.get().setDeleted(true);
            return modelRepository.save(model.get());
        }else {
            throw new InventoryServiceException(ErrorType.MODEL_NOT_FOUND);
        }
    }
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Model findById(String modelId) {
        return modelRepository.findById(modelId).orElseThrow(()-> new InventoryServiceException(ErrorType.MODEL_NOT_FOUND));
    }
}
