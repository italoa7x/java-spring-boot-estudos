package com.springapi.api_ai.controllers.chat;

import javax.validation.constraints.NotNull;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springapi.api_ai.models.Chat;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(@NotNull ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping
    public Chat generation(@RequestBody Chat request) {
        String retorno = this.chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        return new Chat(retorno);
    }

}
