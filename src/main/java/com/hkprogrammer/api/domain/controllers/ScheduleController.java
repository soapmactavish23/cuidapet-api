package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.services.ScheduleService;
import com.hkprogrammer.api.domain.view_models.ScheduleSaveInputModel;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/agendamentos")
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping
    public ResponseEntity<Schedule> save(@RequestBody @Valid ScheduleSaveInputModel inputModel) {
        log.info(inputModel.toString());
        Schedule schedule = service.save(inputModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

}
