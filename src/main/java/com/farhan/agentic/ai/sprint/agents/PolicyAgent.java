package com.farhan.agentic.ai.sprint.agents;

import com.farhan.agentic.ai.sprint.tools.PolicyTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class PolicyAgent {

    private final ChatClient chatClient;

    public PolicyAgent(ChatClient.Builder builder, VectorStore policyVectorStore, ChatMemory chatMemory, PolicyTool policyTool) {
        this.chatClient = builder.defaultSystem("""
                You are an company policy specialist
                """)
                .defaultTools(policyTool)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(policyVectorStore)
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
