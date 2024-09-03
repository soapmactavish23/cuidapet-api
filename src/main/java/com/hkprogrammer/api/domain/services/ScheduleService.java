package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.models.enums.ScheduleStatus;
import com.hkprogrammer.api.domain.repositories.ScheduleRepository;
import com.hkprogrammer.api.domain.view_models.ScheduleSaveInputModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    @Transactional
    public Schedule save(ScheduleSaveInputModel inputModel) {
        return repository.save(inputModel.convert());
    }

    private Schedule findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    @Transactional
    public void changeStatus(Integer id, String status) {
        Schedule schedule = findById(id);
        schedule.setStatus(ScheduleStatus.valueOf(status));
        repository.save(schedule);
    }

}
