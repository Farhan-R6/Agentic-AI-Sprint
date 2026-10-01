package com.farhan.agentic.ai.sprint.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class ResumeLoader {

    @Bean
    CommandLineRunner loadResumeChunkKnowledge(@Qualifier("resumeVectorStore") VectorStore resumeVectorStore) {
        return args -> {

            Resource resource = new ClassPathResource("Doc/resume.pdf");

            PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
            List<Document> pages = reader.get();

            pages.forEach(page -> page.getMetadata().put("filename", resource.getFilename()));

            List<Document> chunks = TokenTextSplitter.builder().build().apply(pages);
            resumeVectorStore.add(chunks);

            System.out.println("Resume Loaded: " + chunks.size() + " chunks");
        };
    }
}
