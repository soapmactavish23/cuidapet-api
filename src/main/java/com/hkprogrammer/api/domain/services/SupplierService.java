package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.dto.SupplierNearbyMeDto;
import com.hkprogrammer.api.domain.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    public List<SupplierNearbyMeDto> findNearByMe(Double lat, Double lng) {
        return repository.findNearByPosition(lat, lng, 5000);
    }

}
