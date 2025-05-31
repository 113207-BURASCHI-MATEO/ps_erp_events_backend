package com.tup.ps.erpevents.clients.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OpenAIResponseDTO {

    private String id;

    private String object;

    private long created;

    private String model;

    private List<Choice> choices;

    @Data
    public static class Choice {

        private int index;

        private Message message;

        private Object logprobs;

        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        private String role;

        private String content;

        private Object refusal;
    }
}
