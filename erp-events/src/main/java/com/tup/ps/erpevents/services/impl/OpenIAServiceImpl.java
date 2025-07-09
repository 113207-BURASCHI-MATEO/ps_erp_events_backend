package com.tup.ps.erpevents.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.clients.OpenIARestTemplate;
import com.tup.ps.erpevents.clients.dtos.OpenAIResponseDTO;
import com.tup.ps.erpevents.services.OpenIAService;
import com.tup.ps.erpevents.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OpenIAServiceImpl implements OpenIAService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OpenIARestTemplate openIARestTemplate;

    @Autowired
    private RedisService redisService;

    @Value(value = "${chatgpt.initial.dashboard-prompt}")
    private String dashboardPrompt;


    @Override
    public String getChatGPTResponse(String request) {
        try {

            /*String redisKey = "chatgpt::" + DigestUtils.md5DigestAsHex(request.getBytes(StandardCharsets.UTF_8));
            Object cachedObject = redisService.getObject(redisKey);
            if (cachedObject != null) {
                return cachedObject.toString();
            }*/

            Map<String, Object> body = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "system", "content", dashboardPrompt),
                            Map.of("role", "user", "content", request)
                    )
            );

            String jsonBody = objectMapper.writeValueAsString(body);
            ResponseEntity<String> response = openIARestTemplate.getChatResponse(jsonBody);

            OpenAIResponseDTO openAIResponse = objectMapper.readValue(response.getBody(), OpenAIResponseDTO.class);

            String chatResponse = openAIResponse.getChoices().get(0).getMessage().getContent();
            //redisService.saveObjectWithExpiration(redisKey, chatResponse, 1.0, TimeUnit.DAYS);

            return chatResponse;

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al obtener respuesta de ChatGPT", e);
        }
    }

}
