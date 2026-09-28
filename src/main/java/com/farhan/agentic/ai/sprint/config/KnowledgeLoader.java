package com.farhan.agentic.ai.sprint.config;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

@Configuration
public class KnowledgeLoader {


    @Bean
    CommandLineRunner loadChunkKnowledge(VectorStore vectorStore, ResourceLoader resourceLoader) {

        return args -> {

            Resource resource = resourceLoader.getResource("classpath:Doc/Company.txt");

            TextReader reader = new TextReader(resource);

            List<Document> documents = reader.get();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(1000)
                    .withKeepSeparator(true)
                    .build();

            List<Document> chunks = splitter.apply(documents);

            vectorStore.add(chunks);

            System.out.println("KNOWLEDGE LOADED VIA RESOURCE");
        };
    }
}
