package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.SupplierService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SupplierServiceViewModel {

    private Integer id;

    private Integer supplierId;

    private String name;

    private Double price;

    public SupplierServiceViewModel(SupplierService supplierService) {
        this.id = supplierService.getId();
        this.supplierId = supplierService.getSupplier().getId();
        this.name = supplierService.getName();
        this.price = supplierService.getPrice();
    }


}
