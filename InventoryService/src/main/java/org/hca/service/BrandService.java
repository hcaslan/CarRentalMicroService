package org.hca.service;

import lombok.RequiredArgsConstructor;
import org.hca.dto.request.BrandSaveRequest;
import org.hca.dto.request.BrandUpdateRequest;
import org.hca.entity.Brand;
import org.hca.exception.ErrorType;
import org.hca.exception.InventoryServiceException;
import org.hca.mapper.BrandMapper;
import org.hca.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public Brand save(BrandSaveRequest brandSaveRequest) {
        Brand brand = brandMapper.dtoToBrand(brandSaveRequest);
        return brandRepository.save(brand);
    }

    public Brand update(BrandUpdateRequest brandUpdateRequest) {
        Brand brand = brandMapper.dtoToBrand(brandUpdateRequest);
        return brandRepository.save(brand);
    }

    public Brand delete(String brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isPresent()) {
            brand.get().setDeleted(true);
            return brandRepository.save(brand.get());
        }else {
            throw new InventoryServiceException(ErrorType.BRAND_NOT_FOUND);
        }
    }
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(String brandId) {
        return brandRepository.findById(brandId).orElseThrow(()-> new InventoryServiceException(ErrorType.BRAND_NOT_FOUND));
    }
}
