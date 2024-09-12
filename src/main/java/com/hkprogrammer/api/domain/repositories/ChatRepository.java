package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    List<Chat> findByScheduleUserId(Integer userId);

    List<Chat> findByScheduleSupplierId(Integer supplierId);

    Optional<Chat> findByScheduleId(Integer scheduleId);

}
