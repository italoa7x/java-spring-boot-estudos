package com.springapi.api_ai.controllers.memory;

import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import com.springapi.api_ai.models.Chat;

import java.util.List;

@RestController
@RequestMapping("/api/chat-memory")
public class MemoryChatController {
    private final MemoryChatService memoryChatService;

    public MemoryChatController(@NotNull MemoryChatService chatService) {
        this.memoryChatService = chatService;

    }

    @PostMapping("/send-message/{chatId}")
    Chat generation(@RequestBody Chat request, @PathVariable String chatId) {
        var response = this.memoryChatService.chat(request.message(), chatId);
        return new Chat(response);
    }

    @PostMapping("/start-chat")
    NewChatResponse startNewChat(@RequestBody Chat chat){
        return memoryChatService.createNewChat(chat.message());
    }

    @GetMapping("/list-chats")
    public List<com.springapi.api_ai.controllers.memory.Chat> listChats(){
        return this.memoryChatService.listAllChats();
    }

    @GetMapping("/list-all-messages/{conversationId}")
    public List<ChatMessage>  listAllMessages(@PathVariable String conversationId){
        return this.memoryChatService.listAllChatMessage(conversationId);
    }

}