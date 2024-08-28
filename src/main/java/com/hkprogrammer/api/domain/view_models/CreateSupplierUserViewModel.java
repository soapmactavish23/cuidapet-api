package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.Category;
import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSupplierUserViewModel {

    @NotBlank
    private String supplierName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Integer categoryId;

    public Supplier convertSupplier() {

        Category category = new Category();
        category.setId(this.categoryId);

        Supplier supplier = new Supplier();
        supplier.setName(supplierName);
        supplier.setCategory(category);

        return supplier;
    }

}
