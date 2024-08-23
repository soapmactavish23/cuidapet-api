package com.hkprogrammer.api.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias_fornecedor")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 45)
    @Column(name = "nome_categoria")
    private String name;

    @NotBlank
    @Size(max = 1)
    @Column(name = "tipo_categoria")
    private String type;

}
