package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.Chat;
import com.hkprogrammer.api.domain.services.ChatService;
import com.hkprogrammer.api.domain.view_models.ChatNotifyViewModel;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService service;

    @PostMapping("/schedule/{scheduleId}/start-chat")
    public ResponseEntity<Chat> startChatByScheduleId(@PathVariable("scheduleId") Integer scheduleId) {
        Chat obj = service.startChat(scheduleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PostMapping("/notify")
    public void notify(@RequestBody @Valid ChatNotifyViewModel viewModel) {

    }

}
