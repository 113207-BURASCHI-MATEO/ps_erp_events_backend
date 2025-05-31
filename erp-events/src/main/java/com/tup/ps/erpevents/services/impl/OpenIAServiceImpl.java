package com.tup.ps.erpevents.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.clients.OpenIARestTemplate;
import com.tup.ps.erpevents.clients.dtos.OpenAIResponseDTO;
import com.tup.ps.erpevents.services.OpenIAService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenIAServiceImpl implements OpenIAService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OpenIARestTemplate openIARestTemplate;

    @Value(value = "${chatgpt.initial.dashboard-prompt}")
    private String dashboardPrompt;


    @Override
    public String getChatGPTResponse(String request) {
        try {
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
            return openAIResponse.getChoices().get(0).getMessage().getContent();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al obtener respuesta de ChatGPT", e);
        }
    }

}
