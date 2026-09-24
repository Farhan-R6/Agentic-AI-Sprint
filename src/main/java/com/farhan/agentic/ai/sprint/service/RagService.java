package com.farhan.agentic.ai.sprint.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder builder) {
        this.vectorStore = vectorStore;
        this.chatClient = builder.build();
    }

    public String ask(String message) {
        List<Document> documents =  vectorStore.similaritySearch(message);

        String context = documents.stream().
                map(Document::getText).
                collect(Collectors.joining("\n"));

        return chatClient.prompt().
                system("""
                Answer only using provided context.
                If answer not found, say
                'I don't know'.
                """).
                user("""
                        Context:
                        %s
                        
                        Question:
                        %s
                        """.formatted(context, message)).
                call().
                content();
    }
}
