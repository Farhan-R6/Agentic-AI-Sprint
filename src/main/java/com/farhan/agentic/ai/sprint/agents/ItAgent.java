package com.farhan.agentic.ai.sprint.agents;

import com.farhan.agentic.ai.sprint.tools.ItTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ItAgent {

    private final ChatClient chatClient;

    public ItAgent(ChatClient.Builder builder, VectorStore itVectorStore, ChatMemory chatMemory, ItTool itTool) {
        this.chatClient = builder.defaultSystem("""
                You are an IT support specialist
                """)
                .defaultTools(itTool)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(itVectorStore)
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
