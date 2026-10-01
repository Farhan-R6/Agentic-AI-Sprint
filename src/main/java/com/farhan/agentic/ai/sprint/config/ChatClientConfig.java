package com.farhan.agentic.ai.sprint.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, @Qualifier("resumeVectorStore")VectorStore vectorStore, ChatMemory chatMemory) {
        return builder.
                defaultSystem("""
                        You are a recruiting assistant. Use only the resume excerpts provided.
                        List each candidate who matches the question, using the name as written
                        in the resume, and explain briefly which skills or experience match.
                        If no candidate matches, say so clearly. Do not invent candidates or skills.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(),

                QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(
                                SearchRequest.builder()
                                .topK(5)
                                .similarityThreshold(0.5)
                                .build())
                        .build()
                        )
                    .build();
    }
}
