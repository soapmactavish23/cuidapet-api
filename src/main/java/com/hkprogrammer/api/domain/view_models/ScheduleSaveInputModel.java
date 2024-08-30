package com.hkprogrammer.api.domain.view_models;

import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.SupplierService;
import com.hkprogrammer.api.domain.models.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleSaveInputModel {

    private Integer id;

    private LocalDateTime scheduleDate;

    private String name;

    private String petName;

    private Integer supplierId;

    private List<Integer> services;

    private Integer userId;

    public Schedule convert() {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        User user = new User();
        user.setId(userId);

        List<SupplierService> supplierServices = services.stream().map((s) -> new SupplierService(s)).toList();

        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setScheduleDate(scheduleDate);
        schedule.setPetName(petName);
        schedule.setName(name);
        schedule.setServices(supplierServices);
        schedule.setUser(user);
        schedule.setSupplier(supplier);

        return schedule;
    }

}
