package com.farhan.agentic.ai.sprint.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class PolicyTool {

    @Tool(description = "Get lastest company policy version")
    public String lastestPolicyVersion() {
        return "Remote work policy v3.2";
    }
}
