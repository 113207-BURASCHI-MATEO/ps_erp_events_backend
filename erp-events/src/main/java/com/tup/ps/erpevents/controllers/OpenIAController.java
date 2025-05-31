package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.services.OpenIAService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ia")
@Validated
@AllArgsConstructor
@Tag(name = "Open IA", description = "Gestión de comunicacion con API de Open IA")
public class OpenIAController {

    @Autowired
    private OpenIAService openIAService;

    @PostMapping("/dashboards")
    public ResponseEntity<String> dashboardSummary(@RequestBody String request) {
        try {
            String result = openIAService.getChatGPTResponse(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud de ChatGPT: " + e.getMessage());
        }
    }

}
