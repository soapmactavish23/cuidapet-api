package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.dto.SupplierNearbyMeDto;
import com.hkprogrammer.api.domain.repositories.SupplierRepository;
import com.hkprogrammer.api.domain.repositories.SupplierServiceRepository;
import com.hkprogrammer.api.domain.view_models.CreateSupplierUserViewModel;
import com.hkprogrammer.api.domain.view_models.SupplierServiceViewModel;
import com.hkprogrammer.api.domain.view_models.UserSaveInputModelDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    private final SupplierServiceRepository supplierServiceRepository;

    private final UserService userService;

    public List<SupplierNearbyMeDto> findNearByMe(Double lat, Double lng) {
        return repository.findNearByPosition(lat, lng, 5000);
    }

    public Supplier findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public List<SupplierServiceViewModel> findServicesBySupplierId(Integer id) {
        List<com.hkprogrammer.api.domain.models.SupplierService> supplierServices = supplierServiceRepository.findBySupplierId(id);
        return supplierServices.stream().map((s) -> new SupplierServiceViewModel(s)).toList();
    }

    public Boolean checkUserExists(String email) {
        return userService.existsUserByEmail(email);
    }

    @Transactional
    public Supplier createUserSupplier(CreateSupplierUserViewModel viewModel) {
        Supplier supplierEntity = save(viewModel.convertSupplier());

        UserSaveInputModelDTO userDTO = new UserSaveInputModelDTO();
        userDTO.setEmail(viewModel.getEmail());
        userDTO.setPassword(viewModel.getPassword());
        userDTO.setSupplierId(supplierEntity.getId());

        userService.createUser(userDTO);

        return supplierEntity;

    }

    private Supplier save(Supplier supplier) {
        return repository.save(supplier);
    }

}
