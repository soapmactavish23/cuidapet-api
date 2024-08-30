package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
