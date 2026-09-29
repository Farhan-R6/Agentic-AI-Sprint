package com.farhan.agentic.ai.sprint.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class ItTool {

    @Tool(description = "Reset user password")
    public String resetPassword(String email) {
        return "Password reset email sent to: " + email;
    }

    @Tool(description = "Check VPN status")
    public String vpnStatus() {
        return "VPN service is healthy";
    }
}
