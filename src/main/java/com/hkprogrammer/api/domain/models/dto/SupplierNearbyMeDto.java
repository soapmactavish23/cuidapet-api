package com.hkprogrammer.api.domain.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public interface SupplierNearbyMeDto {

    Integer getId();

    @JsonProperty("name")
    String getNome();

    String getLogo();

    @JsonProperty("distance")
    Double getDistancia();

    @JsonProperty("categoryId")
    Integer getCategoriasFornecedorId();

}
