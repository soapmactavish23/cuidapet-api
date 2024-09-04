package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.core.security.AuthKeycloakService;
import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.models.enums.ScheduleStatus;
import com.hkprogrammer.api.domain.repositories.ScheduleRepository;
import com.hkprogrammer.api.domain.view_models.ScheduleSaveInputModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;

    private final UserService userService;

    private final AuthKeycloakService authKeycloakService;

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

    public List<Schedule> findAllSchedulesByUser(Authentication authentication) {
        String email = authKeycloakService.getEmailFromToken(authentication);
        Integer userId = userService.findByEmail(email).getId();
        return repository.findByUserIdOrderByScheduleDateDesc(userId);
    }

}
