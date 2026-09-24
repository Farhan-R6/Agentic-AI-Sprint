package com.farhan.agentic.ai.sprint.shared;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class KnowledgeBaseLoader {

    @Bean
    CommandLineRunner loadKnowledge(VectorStore vectorStore) {
        return args -> {
            List<Document> documents =
                    List.of(
                            new Document("""
                                    Skills lab is a microsoft learning organisation
                                    """),
                            new Document("""
                                    Farhan is software engineer
                                    """),
                            new Document("""
                                    Spring AI integrates LLM with spring applications
                                    """)
                    );
            vectorStore.add(documents);
            System.out.println("KNOWLEDGE BASE LOADED");
        };
    }
}
