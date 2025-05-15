package com.springapi.api_ai.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(@NotNull ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    public String generation(String input) {
        return this.chatClient.prompt()
                .user(input)
                .call()
                .content();
    }

}
