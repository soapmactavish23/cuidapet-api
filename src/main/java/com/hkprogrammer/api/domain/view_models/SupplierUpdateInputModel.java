package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.Supplier;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SupplierUpdateInputModel {

    @NotNull
    private Integer supplierId;

    private String name;

    private String logo;

    private String address;

    private String phone;

    private Double lat;

    private Double lng;

    private Integer categoryId;

    public Supplier convert() {
        Supplier supplier = new Supplier();
        supplier.setId(this.supplierId);
        supplier.setName(this.name);
        supplier.setLogo(this.logo);
        supplier.setAddress(this.address);
        supplier.setPhone(this.phone);
        supplier.setLat(this.lat);
        supplier.setLng(this.lng);
        return supplier;
    }

}
