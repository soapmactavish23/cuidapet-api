package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.Chat;
import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.models.dto.ChatDTO;
import com.hkprogrammer.api.domain.models.enums.StatusChat;
import com.hkprogrammer.api.domain.repositories.ChatRepository;
import com.hkprogrammer.api.domain.view_models.ChatNotifyViewModel;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    private final ScheduleService scheduleService;

    private final UserService userService;


    public Chat startChat(Integer scheduleId) {
        Schedule schedule = scheduleService.findById(scheduleId);
        Chat chat = new Chat();
        chat.setSchedule(schedule);
        return repository.save(chat);
    }

    public Chat findById(Integer chatId) {
        return repository.findById(chatId).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public void notifyChat(ChatNotifyViewModel model) {
        Chat chat = findById(model.getChat());

        if(model.getTo() == ChatNotifyViewModel.NotificationUserType.USER) {

        } else {

        }

    }


}
