package com.aigraph.system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for interacting with the DeepSeek AI API.
 */
@Service
public class AIService {
    
    private static final Logger LOGGER = Logger.getLogger(AIService.class.getName());
    
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/v1/chat/completions";
    
    @Value("${deepseek.api.key:sk-9ac456b44e914532bb7fc0fa1bac02a7}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public AIService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Get a response from the DeepSeek AI based on user input.
     * 
     * @param userMessage The user's message
     * @return The AI's response
     */
    public String getResponse(String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);
            
            // Create the request body
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 800);
            
            ArrayNode messages = objectMapper.createArrayNode();
            
            // System message to instruct the model
            ObjectNode systemMessage = objectMapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一个关于人工智能和深度学习的专业助手。请回答用户关于神经网络、机器学习、深度学习等相关领域的问题。请提供详细、准确的解答，并尽可能引用相关概念和研究。对于不在你专业范围内的问题，请礼貌地告知用户你专注于人工智能领域的知识。");
            messages.add(systemMessage);
            
            // Add the user's message
            ObjectNode userMsg = objectMapper.createObjectNode();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.add(userMsg);
            
            requestBody.set("messages", messages);
            
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);
            
            String responseBody = restTemplate.postForObject(DEEPSEEK_API_URL, request, String.class);
            JsonNode responseJson = objectMapper.readTree(responseBody);
            
            // Extract the AI's response from the API response
            String aiResponse = responseJson.path("choices").path(0).path("message").path("content").asText();
            return aiResponse;
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error calling DeepSeek API", e);
            return "很抱歉，我暂时无法回答您的问题。请稍后再试。";
        }
    }
} 