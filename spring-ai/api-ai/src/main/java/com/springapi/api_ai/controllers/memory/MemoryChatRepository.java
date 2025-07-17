package com.springapi.api_ai.controllers.memory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoryChatRepository{

    private JdbcTemplate jdbcTemplate;

    public MemoryChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateChatId(String userId, String description){
        final String sql = "INSERT INTO chat_memory (user_id, description) VALUES (?, ?) RETURNING conversation_id";
        return this.jdbcTemplate.queryForObject(sql, String.class, userId, description);
    }

    public List<Chat> listAllChats(String userId){
        final String sql = "SELECT conversation_id, user_id, description FROM chat_memory WHERE user_id = ?";
        return this.jdbcTemplate.query(sql, (rs, _) -> new Chat(rs.getString("conversation_id"), rs.getString("description")), userId);
    }

    public List<ChatMessage> listAllChatMessages(String conversationId){
        final String sql = "SELECT content, type FROM spring_ai_chat_memory WHERE conversation_id = ?";
        return this.jdbcTemplate.query(sql, (rs, _) -> new ChatMessage(rs.getString("type"), rs.getString("content")), conversationId);
    }
}
