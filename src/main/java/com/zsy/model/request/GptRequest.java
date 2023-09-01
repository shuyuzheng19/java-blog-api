package com.zsy.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class GptRequest implements Serializable {

    private static final String READER = "text-davinci-002-render";

    private static final String READER_SHA = "text-davinci-002-render-sha";

    private static final String MOBILE = "text-davinci-002-render-sha-mobile";

    private String action;
    private List<Message> messages;
    @JsonProperty("parent_message_id")
    private String parentMessageId;

    private String model;

    @Data
    public static class Message {
        private String id;
        private String role;
        private Content content;
    }

    @Data
    public static class Content {
        @JsonProperty("content_type")
        private String contentType;
        private List<String> parts;

        public static Content of(List<String> parts) {
            Content content = new Content();
            content.setContentType("text");
            content.setParts(parts);
            return content;
        }
    }

    private static final String ID = UUID.randomUUID().toString();

    public static GptRequest of(List<String> parts) {
        GptRequest gptRequest = new GptRequest();
        gptRequest.setAction("next");
        gptRequest.setParentMessageId(ID);
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setRole("user");
        message.setContent(Content.of(parts));
        gptRequest.setMessages(List.of(message));
        return gptRequest;
    }
}
