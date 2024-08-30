package com.hkprogrammer.api.domain.models;

import com.hkprogrammer.api.domain.models.enums.ScheduleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "agendamentos")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "data_agendamento")
    private LocalDateTime scheduleDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @NotBlank
    @Size(max = 200)
    @Column(name = "nome")
    private String name;

    @NotBlank
    @Size(max = 200)
    @Column(name = "nome_pet")
    private String petName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Supplier supplier;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "agendamento_servicos",
            joinColumns = @JoinColumn(name = "agendamento"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_servicos"))
    private List<SupplierService> services;

    @PrePersist
    public void prePersist() {
        if(id == null) {
            status = ScheduleStatus.P;

        }
    }

}
