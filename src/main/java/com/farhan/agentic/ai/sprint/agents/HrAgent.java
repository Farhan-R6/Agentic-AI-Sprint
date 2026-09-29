package com.farhan.agentic.ai.sprint.agents;

import com.farhan.agentic.ai.sprint.tools.HrTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class HrAgent {

    private final ChatClient chatClient;

    public HrAgent(ChatClient.Builder builder, VectorStore hrVectorStore, ChatMemory chatMemory, HrTool hrTool) {
        this.chatClient = builder.defaultSystem("""
                You are an HR assistant.
                Answer only HR related questions.
                For leave balance questions, use the getLeaveBalance tool.
                if employeeId is missing, ask user to provide employeeId.
                """).defaultTools(hrTool)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(hrVectorStore)
                                .build())
                .build();
    }

    public String answer(String question, String conversationId) {

        return chatClient.prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

    }
}
