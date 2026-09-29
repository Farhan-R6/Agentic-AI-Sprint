package com.farhan.agentic.ai.sprint.config;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class KnowledgeLoader {


    @Bean
    CommandLineRunner loadChunkKnowledge(@Qualifier("vectorStore")VectorStore vectorStore,
                                         @Qualifier("hrVectorStore") VectorStore hrVectorStore,
                                         @Qualifier("itVectorStore") VectorStore itVectorStore,
                                         @Qualifier("policyVectorStore") VectorStore policyVectorStore) {

        return args -> {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(1000)
                    .withKeepSeparator(true)
                    .build();

            int totalChunks = 0;

            totalChunks += loadInStore(resolver, "classpath:Doc/Company.txt", vectorStore, splitter);
            totalChunks += loadInStore(resolver, "classpath:Doc/hr.txt", vectorStore, splitter);
            totalChunks += loadInStore(resolver, "classpath:Doc/it.txt", vectorStore, splitter);
            totalChunks += loadInStore(resolver, "classpath:Doc/policy.txt", vectorStore, splitter);

            System.out.println("Total chunks loaded: " + totalChunks);
        };
    }

    private int loadInStore(PathMatchingResourcePatternResolver resolver, String path, VectorStore targetStore, TokenTextSplitter splitter) throws Exception {

        Resource[] resources = resolver.getResources(path);

        int chunkLoaded = 0;

        for (Resource resource : resources) {
            TextReader reader = new TextReader(resource);
            List<Document> documents = reader.get();
            List<Document> chunks = splitter.apply(documents);
            targetStore.add(chunks);
            chunkLoaded += chunks.size();
            System.out.println("Loaded " + resource.getFilename() + "-> " + chunks.size() + " chunks");
        }
            return chunkLoaded;
    }
}
