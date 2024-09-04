package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByUserIdOrderByScheduleDateDesc(Integer id);

    List<Schedule> findBySupplierIdOrderByScheduleDateDesc(Integer id);

}
