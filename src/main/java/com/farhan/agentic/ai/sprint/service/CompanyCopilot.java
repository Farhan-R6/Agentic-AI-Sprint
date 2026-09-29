package com.farhan.agentic.ai.sprint.service;

import com.farhan.agentic.ai.sprint.agents.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

@Service
public class CompanyCopilot {

    private final HrAgent hrAgent;
    private final ItAgent itAgent;
    private final PolicyAgent policyAgent;
    private final RouterAgent routerAgent;

    public CompanyCopilot(HrAgent hrAgent, ItAgent itAgent, PolicyAgent policyAgent, RouterAgent routerAgent) {
        super();
        this.hrAgent = hrAgent;
        this.itAgent = itAgent;
        this.policyAgent = policyAgent;
        this.routerAgent = routerAgent;
    }

    public String ask(String conversationId, String question) {

        AgentType agentType = routerAgent.route(question);

        if (agentType == AgentType.HR) {
            return hrAgent.answer(question, conversationId);
        } else if (agentType == AgentType.IT) {
            return itAgent.answer(question, conversationId);
        } else if (agentType == AgentType.POLICY) {
            return policyAgent.answer(question, conversationId);
        }
        throw new IllegalStateException("Unexpected agent type: " + agentType);
    }
}
