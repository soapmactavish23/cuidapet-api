package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.Chat;
import com.hkprogrammer.api.domain.services.ChatService;
import com.hkprogrammer.api.domain.view_models.ChatNotifyViewModel;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void notifyChat(@RequestBody @Valid ChatNotifyViewModel viewModel) {
        service.notifyChat(viewModel);
    }

    @GetMapping("/chats/user")
    public List<Chat> findByUser(Authentication authentication) {
        return service.findByUser(authentication);
    }

    @GetMapping("/supplier")
    public List<Chat> findBySupplier(Authentication authentication) {
        return service.findBySupplier(authentication);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping("/schedule/{scheduleId}/end-chat")
    public void endChat(@PathVariable("scheduleId") Integer scheduleId) {
        service.endChat(scheduleId);
    }

}
