package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.service.RagServiceQnA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ragQnA")
public class RagControllerQnA {

    private final RagServiceQnA ragServiceQnA;

    public RagControllerQnA(RagServiceQnA ragServiceQnA) {
        this.ragServiceQnA = ragServiceQnA;
    }

    @GetMapping
    public String ask(@RequestParam String conversationId,
                      @RequestParam String question) {
        return ragServiceQnA.ask(conversationId, question);
    }
}
