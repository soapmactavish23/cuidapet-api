package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.core.facades.PushNotificationFacade;
import com.hkprogrammer.api.domain.models.Chat;
import com.hkprogrammer.api.domain.models.Schedule;
import com.hkprogrammer.api.domain.models.dto.ChatDTO;
import com.hkprogrammer.api.domain.models.enums.StatusChat;
import com.hkprogrammer.api.domain.repositories.ChatRepository;
import com.hkprogrammer.api.domain.view_models.ChatNotifyViewModel;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    private final ScheduleService scheduleService;

    private final UserService userService;

    private final PushNotificationFacade pushNotificationFacade;

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
        ChatDTO chatDTO = new ChatDTO(chat);
        if(model.getTo() == ChatNotifyViewModel.NotificationUserType.USER) {
            notifyUser(chatDTO.getUserDeviceToken().getTokens(), model, chatDTO);
        } else {
            notifyUser(chatDTO.getSupplierDeviceToken().getTokens(), model, chatDTO);
        }

    }

    private void notifyUser(List<String> tokens, ChatNotifyViewModel model, ChatDTO chat) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "CHAT_MESSAGE");

        Map<String, Object> chatMap = new HashMap<>();
        chatMap.put("id", 123);
        chatMap.put("nome", "Nome do Chat");

        Map<String, Object> fornecedor = new HashMap<>();
        fornecedor.put("nome", "Nome do Fornecedor");
        fornecedor.put("logo", "Logo do Fornecedor");

        chatMap.put("fornecedor", fornecedor);

        payload.put("chat", chatMap);

        pushNotificationFacade.sendMessage(tokens, "Nova Mensagem", model.getMessage(), payload);
    }


}
