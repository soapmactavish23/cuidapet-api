package com.hkprogrammer.api.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.geo.Point;

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

    @Formula("ST_X(latlng)")
    private Double lat;

    @Formula("ST_X(latlng)")
    private Double lng;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categorias_fornecedor_id")
    private Category category;

    @PrePersist
    public void prePersist() {
        if(lat == null || lng == null) {
            lat = 0.0;
            lng = 0.0;
        }
    }

}
