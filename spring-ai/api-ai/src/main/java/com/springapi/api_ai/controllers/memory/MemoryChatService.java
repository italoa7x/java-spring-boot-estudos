package com.springapi.api_ai.controllers.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;
    private final String chatDescription = "Generate a chat description based on message, limiting the description to 30 characters: ";
    private final String userId = "spring-ai-user-id";
    private MemoryChatRepository memoryChatRepository;


    public MemoryChatService(ChatClient.Builder chatClientBuilder, JdbcChatMemoryRepository jdbcChatMemoryRepository, MemoryChatRepository memoryChatRepository) {
        this.memoryChatRepository =  memoryChatRepository;

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository) // Essa linha deixa implicito o tipo do jdbc que esta sendo utilizado.
                .maxMessages(10)
                .build();

        this.chatClient = chatClientBuilder.defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build()
        ).build();
    }

    public String chat(String message, String chatId) {
        return this.chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .user(message)
                .call()
                .content();
    }

    public NewChatResponse createNewChat(String message){
        String description = generateDescriptionChat(message);
        String chatId = this.memoryChatRepository.generateChatId(this.userId, description);
        String response = this.chat(message, chatId);

        return new NewChatResponse(chatId, description, response);
    }

    private String generateDescriptionChat(String message){
        return this.chatClient.prompt()
                .user(chatDescription + message)
                .call()
                .content();
    }

    public List<Chat> listAllChats(){
        return memoryChatRepository.listAllChats(this.userId);
    }

    public List<ChatMessage> listAllChatMessage(String conversationId){
        return memoryChatRepository.listAllChatMessages(conversationId);
    }

}
