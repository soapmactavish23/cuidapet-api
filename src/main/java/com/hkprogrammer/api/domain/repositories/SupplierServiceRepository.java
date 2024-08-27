package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.SupplierService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierServiceRepository extends JpaRepository<SupplierService, Integer> {

    public List<SupplierService> findBySupplierId(Integer id);

}
