package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
