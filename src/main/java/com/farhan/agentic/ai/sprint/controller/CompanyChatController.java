package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.DTO.ChatRequest;
import com.farhan.agentic.ai.sprint.service.CompanyCopilot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class CompanyChatController {

    private final CompanyCopilot companyCopilot;

    public CompanyChatController (CompanyCopilot companyCopilot) {
        super();
        this.companyCopilot = companyCopilot;
    }

    @PostMapping
    public String ask(@RequestBody ChatRequest request) {

        return companyCopilot.ask(request.conversationId(), request.question());
    }

}
