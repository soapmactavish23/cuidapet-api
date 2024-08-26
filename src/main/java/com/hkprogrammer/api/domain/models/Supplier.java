package com.hkprogrammer.api.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "fornecedor")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 200)
    @Column(name = "nome")
    private String name;

    private String logo;

    @Size(max = 100)
    @Column(name = "endereco")
    private String address;

    @Size(max = 45)
    @Column(name = "telefone")
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double lat;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double lng;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categorias_fornecedor_id")
    private Category category;
}
