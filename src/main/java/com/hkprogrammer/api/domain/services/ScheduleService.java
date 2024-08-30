package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.repositories.ScheduleRepository;
import com.hkprogrammer.api.domain.view_models.ScheduleSaveInputModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    public Schedule save(ScheduleSaveInputModel inputModel) {
        return repository.save(inputModel.convert());
    }

}
