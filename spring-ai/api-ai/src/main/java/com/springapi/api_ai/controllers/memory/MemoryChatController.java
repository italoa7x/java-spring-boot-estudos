package com.springapi.api_ai.controllers.memory;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapi.api_ai.models.Chat;

@RestController
@RequestMapping("/api/chat-memory")
public class MemoryChatController {
    private final MemoryChatService memoryChatService;

    public MemoryChatController(@NotNull MemoryChatService chatService) {
        this.memoryChatService = chatService;

    }

    @PostMapping
    Chat generation(@RequestBody Chat request) {
        var response = this.memoryChatService.simpleChat(request.message());
        return new Chat(response);
    }
}