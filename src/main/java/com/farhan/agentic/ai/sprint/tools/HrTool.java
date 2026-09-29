package com.farhan.agentic.ai.sprint.tools;

import com.farhan.agentic.ai.sprint.service.LeaveService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class HrTool {

    private final LeaveService leaveService;

    public HrTool(LeaveService leaveService) {
        super();
        this.leaveService = leaveService;
    }

    @Tool(description = "Get employee leave balance")
    public String getLeaveBalance(String employeeId) {

        return "balance " + leaveService.getBalance(employeeId);

    }
}
