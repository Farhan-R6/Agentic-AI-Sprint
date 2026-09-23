package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.service.chatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class chatController {

    private final chatService chatService;

    public chatController(chatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return chatService.ask(message);
    }
}
