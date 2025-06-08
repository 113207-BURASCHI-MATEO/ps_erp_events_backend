package com.tup.ps.erpevents.clients;

import com.tup.ps.erpevents.services.RedisService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@NoArgsConstructor
@Service
public class OpenIARestTemplate {

    private static final String RESILIENCE4J_INSTANCE_NAME = "circuitIA";
    private static final String FALLBACK_METHOD = "fallback";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<String> getChatResponse(String jsonBody) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            return restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al llamar a OpenAI: " + ex.getMessage(), ex);
        }
    }

    public ResponseEntity<String> fallback(Exception ex) {
        handleFallbackLog(ex);
        if (ex instanceof HttpClientErrorException httpException) {
            if (httpException.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + ex.getMessage());
            } else if (httpException.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + ex.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    private void handleFallbackLog(Exception ex) {
        if (ex instanceof HttpClientErrorException httpException) {
            if (httpException.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("⛔ IA | Resource not found: " + ex.getMessage());
            } else if (httpException.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.out.println("⛔ IA | Bad request: " + ex.getMessage());
            }
        } else {
            System.out.println("⛔ IA | Service unavailable: " + ex.getMessage());
        }
    }
}
