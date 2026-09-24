package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.service.ChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return chatService.ask(message);
    }
}
