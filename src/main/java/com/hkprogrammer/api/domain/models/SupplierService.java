package com.hkprogrammer.api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "fornecedor_servicos")
public class SupplierService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fornecedor_id")
    private Supplier supplier;

    @NotBlank
    @Size(max = 200)
    @Column(name = "nome_servico")
    private String name;

    @NotNull
    @Column(name = "valor_servico")
    private Double price;

}
